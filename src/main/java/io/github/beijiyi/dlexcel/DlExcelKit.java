//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.github.beijiyi.dlexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DlExcelKit {
    private int start = 0;
    private int end = 0;
    private Integer sheet = 0;
    private String sheetName = "";
    private Map<Integer, String> headNames = new HashMap();

    public DlExcelKit() {
    }

    public static DlExcelKit getDlExcelKit() {
        return new DlExcelKit();
    }

    public DlExcelKit start(int start) {
        this.start = start;
        return this;
    }

    public DlExcelKit end(int end) {
        this.end = end;
        return this;
    }

    public DlExcelKit sheet(Integer sheet) {
        if (sheet != null && sheet >= 0) {
            this.sheet = sheet;
        }

        return this;
    }

    public DlExcelKit sheetName(String sheetName) {
        if (sheetName != null && !"".equals(sheetName)) {
            this.sheetName = sheetName;
        }

        return this;
    }

    public DlExcelKit heads(String... sheetName) {
        if (sheetName != null && !"".equals(sheetName)) {
            for(int i = 0; i < sheetName.length; ++i) {
                this.headNames.put(i, sheetName[i]);
            }
        }

        return this;
    }

    public List<Map> read(String fileName, Integer start) {
        return this.read(fileName, start, (Integer)null);
    }

    public List<Map> read(String fileName, Integer start, Integer end) {
        final List<Map> retList = new ArrayList();
        Integer starti = start != null ? start : this.start;
        final Integer endi = end != null ? end : this.end;
        ((ExcelReaderSheetBuilder) EasyExcel.read(fileName, new AnalysisEventListener<Map<Integer, String>>() {
            public void invoke(Map<Integer, String> o, AnalysisContext analysisContext) {
                if (DlExcelKit.this.headNames != null && DlExcelKit.this.headNames.size() > 0) {
                    Map<String, String> temMap = new HashMap();
                    o.forEach((k, v) -> {
                        if (DlExcelKit.this.headNames.containsKey(k)) {
                            temMap.put(DlExcelKit.this.headNames.get(k), v);
                        } else {
                            temMap.put(String.valueOf(k), v);
                        }

                    });
                    retList.add(temMap);
                } else {
                    retList.add(o);
                }

            }

            public void doAfterAllAnalysed(AnalysisContext context) {
                if (endi != null & endi > 0) {
                    int i = retList.size();
                    if (endi >= i) {
                        retList.clear();
                    } else {
                        List<Map> retList2 = retList.subList(i - endi, i);
                        retList.removeAll(retList2);
                    }
                }

            }
        }).sheet().headRowNumber(starti)).doRead();
        return retList;
    }
}
