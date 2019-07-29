package com.changfa.frame.service.util.excel;

public enum ExcelSheetSettingEnum {
    REPORT_TEST("report_test", "智慧酒庄充值详情", new String[]{"智慧酒庄充值详情"}, new String[]{"智慧酒庄充值详情"}, new String[][]{
            {"日期", "充值金额", "实收金额", "奖励金额"}
    }),
    REPORT_TEST2("report_test2", "智慧酒庄充值详情", new String[]{"智慧酒庄充值详情", "标题2"}),
    REPORT_TEST3("report_test3", "Excel文件名称");

    ExcelSheetSettingEnum(String code, String filename) {
        this.code = code;
        this.filename = filename;
    }

    ExcelSheetSettingEnum(String code, String filename, String[] titles) {
        this.code = code;
        this.filename = filename;
        this.titles = titles;
    }

    ExcelSheetSettingEnum(String code, String filename, String[] sheetnames, String[] titles, String[][] headers) {
        this.code = code;
        this.filename = filename;
        this.sheetnames = sheetnames;
        this.titles = titles;
        this.headers = headers;
    }

    /**
     * 代码标识(必选)
     */
    private String code;

    /**
     * 代码标识(必选)
     */
    private String filename;

    /**
     * Sheet名称(可选)
     */
    private String[] sheetnames;

    /**
     * Sheet标题(可选)
     */
    private String[] titles;

    /**
     * 表头名称(可选)
     */
    private String[][] headers;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String[] getSheetnames() {
        return sheetnames;
    }

    public void setSheetnames(String[] sheetnames) {
        this.sheetnames = sheetnames;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public String[][] getHeaders() {
        return headers;
    }

    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }
}
