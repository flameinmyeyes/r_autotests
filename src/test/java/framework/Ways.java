package framework;

public enum Ways {
    COMMON("Z:\\files_for_tests\\"),
    DOWNLOADS("Z:\\files_for_tests\\downloads\\"),
    UIDM_DEV("Z:\\files_for_tests\\eb_exp_demo"),
    TEST("files_for_tests/test"),

    //
    TSE_DEMO_28080("Z:\\files_for_tests\\eb_exp_demo")

    ;
    private String way;
    Ways(String way) {
        this.way = way;
    }
    public String getWay() {
        return way;
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
