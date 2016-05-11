package ios.pages

import groovy.util.logging.Slf4j
import io.appium.java_client.MobileElement
import io.appium.java_client.pagefactory.iOSFindBy
import org.junit.Assert

/**
 * Created by maretska on 2/23/16.
 */
@Slf4j
class HeaderPage extends AbstractPage {

    @iOSFindBy(xpath="//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAImage[3]")
    static public MobileElement header

    @iOSFindBy(xpath="//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAImage[3]")
    static public MobileElement hamburgerIcon

    @iOSFindBy(xpath="//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAImage[9]")
    static public MobileElement asal

    public static void hamburgerClick() {
        log.info 'Click Hamburger Bar'
        Assert.assertEquals(hamburgerIcon.isDisplayed(), true)
        hamburgerIcon.click()
    }

    public static void headerClick() {
        log.info "Click Header"
        Assert.assertEquals(header.isDisplayed(), true)
        header.click()
    }

}
