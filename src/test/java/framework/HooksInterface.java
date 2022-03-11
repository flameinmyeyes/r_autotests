package framework;

public interface HooksInterface {

//    public DriverInit setDriver();
    public void setUp();
//    public void returnStartPage();
    public void completeTest();

//    default String getDefaultPath(){
//        return getPackage() + "\\" +  getNameTest();
//    }
//
//    default String getPackage(){
//        String path = this.getClass().getPackage().toString();
//        String[] elemPath = path.split("\\.");
//        return elemPath[elemPath.length -1];
//    }
//
//    default String getNameTest(){
//        return this.getClass().getSimpleName();
//    }

}
