package com.uam;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class LoanCharts {

    static class LoanRecord {
        LocalDate issueDate;
        double loanAmount;
        double totalPayment;
        String addressState;
        String term;

        LoanRecord(LocalDate d, double la, double tp, String st, String term) {
            this.issueDate = d;
            this.loanAmount = la;
            this.totalPayment = tp;
            this.addressState = st;
            this.term = term;
        }
    }

    static List<LoanRecord> readExcelFromResources(String resourcePath) throws Exception {
        try (InputStream in = LoanCharts.class.getResourceAsStream(resourcePath);
             Workbook wb = new XSSFWorkbook(Objects.requireNonNull(in))) {

            Sheet sheet = wb.getSheetAt(0);
            Row header = sheet.getRow(0);

            Map<String, Integer> col = new HashMap<>();
            for (Cell c : header) {
                col.put(c.getStringCellValue().trim(), c.getColumnIndex());
            }

            DataFormatter fmt = new DataFormatter();
            List<LoanRecord> out = new ArrayList<>();

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                LocalDate issueDate = readDateCell(row.getCell(col.get("issue_date")));
                double loanAmount = readDouble(row.getCell(col.get("loan_amount")));
                double totalPayment = readDouble(row.getCell(col.get("total_payment")));
                String state = fmt.formatCellValue(row.getCell(col.get("address_state")));
                String term = fmt.formatCellValue(row.getCell(col.get("term")));

                if (issueDate != null) {
                    out.add(new LoanRecord(issueDate, loanAmount, totalPayment, state, term));
                }
            }
            return out;
        }
    }

    static LocalDate readDateCell(Cell c) {
        if (c == null) return null;

        if (c.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(c)) {
            return c.getLocalDateTimeCellValue().toLocalDate();
        }

        String s = new DataFormatter().formatCellValue(c).trim();
        if (s.isEmpty()) return null;

        DateTimeFormatter[] fmts = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy")
        };

        for (DateTimeFormatter f : fmts) {
            try { return LocalDate.parse(s, f); }
            catch (Exception ignored) {}
        }

        try { return LocalDate.parse(s); }
        catch (Exception e) { return null; }
    }

    static double readDouble(Cell c) {
        if (c == null) return 0.0;
        if (c.getCellType() == CellType.NUMERIC) return c.getNumericCellValue();

        String s = new DataFormatter().formatCellValue(c).replace(",", "").trim();
        if (s.isEmpty()) return 0.0;
        return Double.parseDouble(s);
    }

    static TimeSeriesCollection monthlySeries(List<LoanRecord> data,
                                              ToDoubleFunction<LoanRecord> valueFn,
                                              String seriesName) {

        Map<YearMonth, Double> sums = data.stream()
                .collect(Collectors.groupingBy(
                        r -> YearMonth.from(r.issueDate),
                        TreeMap::new,
                        Collectors.summingDouble(valueFn)
                ));

        TimeSeries ts = new TimeSeries(seriesName);
        for (var e : sums.entrySet()) {
            YearMonth ym = e.getKey();
            double vMillions = e.getValue() / 1_000_000d;
            ts.add(new Month(ym.getMonthValue(), ym.getYear()), vMillions);
        }

        TimeSeriesCollection ds = new TimeSeriesCollection();
        ds.addSeries(ts);
        return ds;
    }

    static void showMonthlyAreaLineChart(List<LoanRecord> data,
                                         ToDoubleFunction<LoanRecord> valueFn,
                                         String title,
                                         String yLabel) {

        TimeSeriesCollection ds = monthlySeries(data, valueFn, yLabel);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                title, "Month", yLabel, ds,
                false, true, false
        );

        XYPlot plot = chart.getXYPlot();

        plot.setDataset(0, ds);
        plot.setRenderer(0, new XYAreaRenderer());

        plot.setDataset(1, ds);
        plot.setRenderer(1, new XYLineAndShapeRenderer(true, false));

        ChartFrame frame = new ChartFrame(title, chart);
        frame.pack();
        frame.setVisible(true);
    }

    static void showStateBarChart(List<LoanRecord> data) {

        Map<String, Double> byState = data.stream()
                .collect(Collectors.groupingBy(
                        r -> r.addressState,
                        Collectors.summingDouble(r -> r.loanAmount)
                ));

        DefaultCategoryDataset ds = new DefaultCategoryDataset();

        byState.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> ds.addValue(e.getValue() / 1000d, "Funded (K)", e.getKey()));

        JFreeChart chart = ChartFactory.createBarChart(
                "Total Funded Amount by State (Thousands)",
                "Funded (₹ '000)",
                "State",
                ds,
                PlotOrientation.HORIZONTAL,
                false, true, false
        );

        ChartFrame frame = new ChartFrame("State Funding", chart);
        frame.pack();
        frame.setVisible(true);
    }

    static void showTermDonutChart(List<LoanRecord> data) {

        Map<String, Double> byTerm = data.stream()
                .collect(Collectors.groupingBy(
                        r -> r.term,
                        Collectors.summingDouble(r -> r.loanAmount)
                ));

        DefaultPieDataset pie = new DefaultPieDataset();
        byTerm.forEach((term, val) -> pie.setValue(term, val / 1_000_000d));

        JFreeChart chart = ChartFactory.createRingChart(
                "Total Funded Amount by Term (Millions)",
                pie, true, true, false
        );

        RingPlot plot = (RingPlot) chart.getPlot();
        plot.setSectionDepth(0.35);

        ChartFrame frame = new ChartFrame("Term Funding", chart);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        List<LoanRecord> data = readExcelFromResources("/data/financial_loan.xlsx");

        showMonthlyAreaLineChart(
                data,
                r -> r.loanAmount,
                "Total Funded Amount by Month",
                "Funded Amount (₹ Millions)"
        );

        showMonthlyAreaLineChart(
                data,
                r -> r.totalPayment,
                "Total Received Amount by Month",
                "Received Amount (₹ Millions)"
        );

        showStateBarChart(data);
        showTermDonutChart(data);
    }
}
