package ios.pages

import commons.BaseAcceptanceTest
import groovy.util.logging.Slf4j
import io.appium.java_client.MobileElement
import io.appium.java_client.pagefactory.iOSFindBy
import org.openqa.selenium.support.ui.ExpectedConditions

/**
 * Created by maretska on 2/22/16.
 */
@Slf4j
class AbstractPage {
    @iOSFindBy(xpath = '//UIAApplication[1]/UIAWindow[1]/UIAActivityIndicator[1]/UIAImage[1]' )
    public static MobileElement pageLoad
}
