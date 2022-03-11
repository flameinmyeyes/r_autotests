package framework;

public enum Ways {
    COMMON("Z:\\files_for_tests\\"),
    JOB_JIRA("Z:\\files_for_tests\\job_jira"),
    DOWNLOADS("Z:\\files_for_tests\\downloads\\"),

    //ПУР ЭБ
    EXP_DEMO("Z:\\files_for_tests\\eb_exp_demo"),
    EXP_TEST("Z:\\files_for_tests\\eb_exp_test"),
    EXP_TEST_N1("Z:\\files_for_tests\\eb_exp05fb_test_n1_ufos"),
    EXP_TEST_N2("Z:\\files_for_tests\\eb_exp05fb_test_n2_ufos"),
    GORODETS(".\\files_for_tests\\gorodets"),
    SKP_KBRN("Z:\\files_for_tests\\eb_skp_kbrn"),
    EMC_TEST_UFOS("Z:\\files_for_tests\\eb_emc_test"),
    EXP_TEST_P1("Z:\\files_for_tests\\eb_exp05fb_test_p1_ufos"),

    //ПУР КС
    TSE_DEMO("Z:\\files_for_tests\\eb_tse_demo"),
    TSE_DEMO_28080("Z:\\files_for_tests\\eb_tse_demo_28080"),
    FEDOROV_TSE_DEMO("Z:\\files_for_tests\\eb_tse_demo\\fedorov_alexander\\"),

    //ПУиО
    ARP_DEV_UFOS("Z:\\files_for_tests\\eb_arp_dev_ufos"),
    ARP_DEV_SSO("Z:\\files_for_tests\\eb_arp_dev_sso"),

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
