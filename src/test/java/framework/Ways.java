package framework;

import static com.codeborne.selenide.Configuration.downloadsFolder;

public enum Ways {
    COMMON("files_for_tests"),
    DOWNLOADS_WIN("/share/files_for_tests/downloads"),
    DOWNLOADS_MAC("*/rec_autotests/src/"),
    DEV("files_for_tests/dev"),
    TEST("files_for_tests/test"),
    ;

    private String way;
    Ways(String way) {
        this.way = way;
    }
    public String getWay() {
        return way;
    }

    public static String getOS(){
        if (System.getProperty("os.name").contains("Windows")){
            return DOWNLOADS_WIN.getWay();
        } else if (System.getProperty("os.name").contains("Mac")){
            return DOWNLOADS_MAC.getWay();
        }
        return null;
    }

    /**
     * Преобразование пути к файлу под Linux (в случае запуска в докере)
     */
    public static String setWay(String runMode, String way) {
        if (runMode.equals("docker")) {
            way = way.replace("Z:", "/share").replace("\\", "/");
            //System.out.println("Путь к файлам преобразован под Linux");
        } else {
            //System.out.println("Преобразование пути к файлам под Linux не требуется");
        }
        return way;
    }
}
