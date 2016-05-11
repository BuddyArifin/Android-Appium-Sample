package PageObject

import org.openqa.selenium.WebDriver

/**
 * Created by latifah on 6/10/2015.
 */
class Initializations {
    static def WebDriver driver

//    static void createAppiumInstance() {
//        DesiredCapabilities capabilities = new DesiredCapabilities()
//            capabilities.setCapability(MobileCapabilityType.PLATFORM, SampleModules.PLATFORM)
//            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, SampleModules.DEVICEVERSION)
//            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, SampleModules.DEVICENAME)
//            capabilities.setCapability(MobileCapabilityType.APP, SampleModules.APPPATH)
//            capabilities.setCapability("autoAcceptAlerts", true)
//            capabilities.setCapability("waitForAppActivity", true)
//            driver = new IOSDriver(new URL("http://localhost:4723/wd/hub"), capabilities)
//
//            driver.setFileDetector(new LocalFileDetector())
//            driver.manage().timeouts().implicitlyWait(SampleModules.IMPLICITWAIT, TimeUnit.SECONDS)
//    }
//
//    @BeforeClass
//    static void initServer() {
//        Initializations.createAppiumInstance()
//    }
//
//    @AfterClass
//    public static void closeSession(){
//        driver.quit()
//    }

}
