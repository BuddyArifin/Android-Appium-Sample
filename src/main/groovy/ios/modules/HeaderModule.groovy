package ios.modules

import io.appium.java_client.MobileElement
import io.appium.java_client.pagefactory.iOSFindBy
import ios.pages.AbstractPage

/**
 * Created by maretska on 2/22/16.
 */
class HeaderModule extends AbstractPage{

    @iOSFindBy(xpath="//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAImage[3]")
    static public MobileElement header

    @iOSFindBy(xpath="//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAImage[3]")
    static public MobileElement hamburgerIcon
}
