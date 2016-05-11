package commons

import groovy.util.logging.Slf4j
import io.appium.java_client.MobileDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.pagefactory.AppiumFieldDecorator
import io.appium.java_client.remote.MobileCapabilityType
import ios.pages.AbstractPage
import org.junit.AfterClass
import org.junit.BeforeClass
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.LocalFileDetector
import org.openqa.selenium.support.PageFactory


import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.TimeUnit

/**
 * Created by maretska on 2/16/16.
 */
@Slf4j
class BaseAcceptanceTest {

    static def MobileDriver driver
    static def AbstractPage page
    static def DesiredCapabilities capabilities
    static def String platform


    static void createInstanceAppium() {
        capabilities = new DesiredCapabilities()
        capabilities.setCapability(MobileCapabilityType.PLATFORM, Constants.PLATFORM)
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, Constants.DEVICEVERSION)
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Constants.DEVICENAME)
        capabilities.setCapability(MobileCapabilityType.APP, Constants.APPPATH)
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, Constants.COMMAND_TIMEOUT)
        capabilities.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT, Constants.LAUNCH_TIMEOUT)
        capabilities.setCapability("autoAcceptAlerts", true)
        capabilities.setCapability("waitForAppActivity", true)
        log.info 'Initialize Appium driver'
        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities)

        driver.setFileDetector(new LocalFileDetector())
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICITWAIT, TimeUnit.SECONDS)
    }

    static void createAndroidInstanceAppium() {
        capabilities = new DesiredCapabilities()
        capabilities.setCapability(MobileCapabilityType.PLATFORM, Constants.ANDROIDPLATFORM)
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, Constants.ANDROIDDEVICEVERSION)
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Constants.DEVICEANDROID)
        capabilities.setCapability(MobileCapabilityType.APP, Constants.ANDROIDPATH)
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, Constants.COMMAND_TIMEOUT)
        capabilities.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT, Constants.LAUNCH_TIMEOUT)
        capabilities.setCapability("autoAcceptAlerts", true)
        capabilities.setCapability("waitForAppActivity", true)
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities)
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICITWAIT, TimeUnit.SECONDS)
    }


    public static setPlatform(String platform) {
        this.platform = platform
    }

    public static String getPlatform() {
        platform
    }

    public static <T> T loadPage(Class<T> aClass) {
        log.info "Load Class..."
        T obj = createInstance(aClass)
        PageFactory.initElements(new AppiumFieldDecorator(driver),obj)
        obj
    }

    public static boolean isNoElementFound() {
        if (NoSuchElementException.class.desiredAssertionStatus()) {
            return true
        } else {
            return false
        }
    }

    public static void scroll() {}

    public static <T> T waitFor(Closure<T> block) {
        def count = 0
        def timeout = 60
        def pass = block()

        while (!isNoElementFound() || pass) {
            log.info 'count :'+ count.toString()
            try {
                if (isNoElementFound()) {
                    sleep(1000)
                    count++
                    pass = isNoElementFound()
                    log.info 'Element not found'
                } else if (pass) {
                    sleep(1000)
                    count++
                    pass
                    log.info 'Element found'
                    break
                } else if (count == timeout) {
                    log.info 'wait element timeout'
                    break
                }
            } catch (Exception e) {
                e.printStackTrace()
            }
        }

        pass

    }

    public void waitPageToHide(MobileElement element) {
        sleep(1000)
        try{
            if (element.isDisplayed()) {
                log.info 'Step: Waiting for page loader to hide'
                waitFor { element?.isDisplayed() }
            }
        }catch (StaleElementReferenceException) {
            log.info 'WARN: Caught stale element exception with page loader'
        }
    }

    public static WebDriver getDriver() {
        return driver
    }

    public static <T> T createInstance(Class<T> aClass) {
        try {
            try {
                Constructor<T> constructor = aClass.getConstructor(AbstractPage)
                return constructor.newInstance(page)
             }catch (NoSuchMethodException e) {
                return aClass.newInstance()
             }
        } catch (InstantiationException e) {
            throw new RuntimeException(e)
        }catch (IllegalAccessException e) {
            throw new RuntimeException(e)
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e)
        }
    }

    @BeforeClass
    static void initServer() {
       if (Constants.APPPATH == null && Constants.DEVICENAME == null &&
               Constants.PLATFORM == null && Constants.DEVICEVERSION == null ) {
            // Run Android Instance Appium
           createAndroidInstanceAppium()
       }
       else if (Constants.ANDROIDPLATFORM == null && Constants.ANDROIDPATH == null &&
                    Constants.ANDROIDDEVICEVERSION == null && Constants.DEVICEANDROID == null) {
            // Run IOS Instance Appium
           createInstanceAppium()
       }
    }

    @AfterClass
    public static void closeSession(){
        log.info 'Simulator Quit'
        driver.quit()
    }

}
