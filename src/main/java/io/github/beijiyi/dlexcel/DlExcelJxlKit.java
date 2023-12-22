//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.github.beijiyi.dlexcel;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.github.beijiyi.dlutil.dlmap.DlMap;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class DlExcelJxlKit {
    private int START = 0;
    private int END = 0;
    private int PAGE_INDEX = 0;

    public DlExcelJxlKit() {
    }

    public static DlExcelJxlKit obj() {
        return new DlExcelJxlKit();
    }

    public List<DlMap> find(File file, String... keyNames) {
        return this.find(file, (Integer)null, (Integer)null, (Integer)null, keyNames);
    }

    public List<DlMap> find(File file, Integer pageIndex, String... keyNames) {
        return this.find(file, (Integer)null, (Integer)null, pageIndex, keyNames);
    }

    public List<DlMap> find(File file, Integer start, Integer end) {
        return this.find(file, start, end, (String[])null);
    }

    public List<DlMap> find(File file) {
        return this.find(file, (Integer)null, (Integer)null, (Integer)null, (String[])null);
    }

    public List<DlMap> find(File file, Integer start, Integer end, String... keyNames) {
        return this.find(file, start, end, (Integer)null, keyNames);
    }

    public List<DlMap> find(File file, Integer start, Integer end, Integer pageIndex, String... keyNames) {
        List list = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            list = this.readInNull(fis, start, end, pageIndex, keyNames);
            return list;
        } catch (Exception var11) {
            var11.printStackTrace();
            return list;
        } finally {
            ;
        }
    }

    private List<DlMap> readInNull(InputStream pIs, Integer start, Integer end, Integer pageIndex, String... keyNames) throws Exception {
        if (isEmpty(pageIndex) || pageIndex < 0) {
            pageIndex = 0;
        }

        if (isEmpty(start) || start < 0) {
            start = this.START;
        }

        if (isEmpty(end) || end < 0) {
            end = this.END;
        }

        boolean isEmptyKeyName = false;
        if (isEmpty(keyNames) || keyNames.length <= 0) {
            isEmptyKeyName = true;
        }

        ArrayList list = new ArrayList();

        try {
            Workbook workbook = Workbook.getWorkbook(pIs);
            Sheet sheet = workbook.getSheet(pageIndex);
            int rows = sheet.getRows();
            int columns = sheet.getColumns();
            int cs;
            if (isEmptyKeyName) {
                cs = columns;
            } else {
                cs = keyNames.length;
            }

            for(int i = start; i < rows - end; ++i) {
                DlMap map = DlMap.createLinkedHashMap();
                Cell[] cells = sheet.getRow(i);

                for(int j = 0; j < cs; ++j) {
                    String key = isEmptyKeyName ? j + "" : keyNames[j];
                    if (cells.length <= j) {
                        map.put(key, "");
                    } else if (cells != null && cells[j] != null) {
                        String v = cells[j].getContents();
                        map.put(key, v == null ? "" : v.trim());
                    } else {
                        map.put(key, "");
                    }
                }

                list.add(map);
            }
        } catch (Exception var19) {
            var19.printStackTrace();
        }

        return list;
    }

    public int getSTART() {
        return this.START;
    }

    public DlExcelJxlKit setSTART(int START) {
        this.START = START;
        return this;
    }

    public int getEND() {
        return this.END;
    }

    public DlExcelJxlKit setEND(int END) {
        this.END = END;
        return this;
    }

    public int getPAGE_INDEX() {
        return this.PAGE_INDEX;
    }

    public DlExcelJxlKit setPAGE_INDEX(int PAGE_INDEX) {
        this.PAGE_INDEX = PAGE_INDEX;
        return this;
    }

    private static boolean isEmpty(Object pObj) {
        if (pObj == null) {
            return true;
        } else if (pObj == "") {
            return true;
        } else {
            if (pObj instanceof String) {
                String empString = (String)pObj;
                empString = empString.trim();
                if (empString.length() == 0 || empString.equals("null")) {
                    return true;
                }
            } else if (pObj instanceof Collection) {
                if (((Collection)pObj).size() == 0) {
                    return true;
                }
            } else if (pObj instanceof Map) {
                if (((Map)pObj).size() == 0) {
                    return true;
                }
            } else if (pObj instanceof Object[] && ((Object[])((Object[])pObj)).length == 0) {
                return true;
            }

            return false;
        }
    }

    private static boolean isNotEmpty(Object pObj) {
        return !isEmpty(pObj);
    }
}
