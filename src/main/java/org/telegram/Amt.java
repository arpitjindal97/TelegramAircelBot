package org.telegram;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.security.UserAndPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.*;

public class Amt
{

    //static ChromeDriver driver;
    static FirefoxDriver driver;
    static WebDriverWait wait;
    static Robot robot;

    static
    {
        //System.setProperty("webdriver.chrome.driver", "/home/arpit/Desktop/temp/bin/linux/googlechrome/64bit/chromedriver");
        //System.setProperty("webdriver.chrome.driver", "/home/pi/Desktop/chromedriver");
        System.setProperty("webdriver.gecko.driver", "geckodriver");

    }

    static Logger log = LoggerFactory.getLogger(Amt.class.getName());

    public static String getDetails(String num)
    {
        log.info("Message Recieved : " + num);
        String res = "";
        try
        {
            driver = new FirefoxDriver();
            driver.manage().deleteAllCookies();

            wait = new WebDriverWait(driver, 15);
            log.info("Opening AMT");
            driver.navigate().to("https://" + //Database.amt_username+":"+ Database.amt_password+"@" +
                    "amt.aircel.co.in/AMTIDAM/Default.aspx");

            Alert alert = driver.switchTo().alert();

            log.info("Switched to alert box");
            try
            {
                robot = new Robot();
                alert.sendKeys(Database.amt_username);
                Thread.sleep(200);

                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);

                Thread.sleep(200);

                int code = 0;
                for (int i = 0; i < Database.amt_password.length(); i++)
                {
                    type(Database.amt_password.charAt(i));
                }

                doType(VK_ENTER);
                Thread.sleep(200);

            } catch (Exception e)
            {
                e.printStackTrace();
            }
            log.info("Log in button clicked");

            driver.switchTo().defaultContent();

            try
            {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("LinkButton1")));
                driver.findElement(By.id("LinkButton1")).click();
                log.info("Log in Success");
            } catch (Exception e)
            {
                log.warn("Already sign in page missed");
            }
            try
            {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
            }
            catch(Exception rfrf)
            {
                log.info("btnSearch not found");
                wait.until(ExpectedConditions.elementToBeClickable(By.id("LinkButton1")));
                log.info("alreay login page reappeared");
                driver.findElement(By.id("LinkButton1")).click();
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
            driver.findElement(By.name("txtSearch")).sendKeys(num);
            driver.findElement(By.name("btnSearch")).click();
            List<WebElement> list = null, heads = null;
            try
            {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("grvCEFPending_ctl02_lnkMSISDN")));
                list = driver.findElements(By.className("grid_content_alt"));
                heads = driver.findElements(By.className("grid_Head"));
            } catch (Exception hdbj)
            {
                return "not";
            }
            List<String> values = new ArrayList<String>();

            if (list == null)
            {
                res = ("No Records Found");
                return res;
            }
            int i;
            for (i = 0; i < list.size(); i++)
            {

                /*if (list.size() == 7 && i + 1 == 7) {
                    temp = short_this(temp);
                    i = 8;

                } else if (i + 1 == 9) {
                    temp = short_this(temp);
                }*/

                res = res + heads.get(i).getText() + " : " + list.get(i).getText() + "\n";
            }
            if (list.size() == 7)
                return res;

            driver.findElement(By.id("grvCEFPending_ctl02_lnkMSISDN")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("cbActView")));
            driver.findElement(By.id("cbActView")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("lnkCEFForm")));
            driver.findElement(By.id("lnkCEFForm")).click();
            ;
            Thread.sleep(1000);

            ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs2.get(1));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("btnCancel")));

            String date = driver.findElement(By.name("txtCEFDate")).getAttribute("value");

            log.info(res);

            res = res + "Date : " + date;
            log.info(res);

        } catch (Exception ee)
        {
            ee.printStackTrace();
            log.error("Invalid AMT user or pass");
            res = "not";
        } finally
        {
            log.info("Closing browser");
            driver.quit();
        }
        return res;
    }

    public static String short_this(String str)
    {
        if (str.equals("SIM ACTIVATED-TV PENDING"))
        {
            return "TVP";
        } else if (str.equals("SERVICE ACTIVATED"))
        {
            return "SAC";
        } else if (str.equals("DEDUP VALIDATION REJECTED"))
        {
            return "DVR";
        } else if (str.equals("DEDUPE VALIDATION PENDING"))
        {
            return "DVP";
        } else if (str.equals("CEF AWAITED"))
        {
            return "CFA";
        } else if (str.equals("SIM ACTIVATION PENDING"))
        {
            return "SAP";
        } else if (str.equals("ACTIVATION CENTRE REJECTED"))
        {
            return "ACR";
        } else if (str.contains("TV REJECTED"))
        {
            return "TVR";
        } else
        {
            return str;
        }
    }

    public static void type(char character)
    {
        switch (character)
        {
            case 'a':
                doType(VK_A);
                break;
            case 'b':
                doType(VK_B);
                break;
            case 'c':
                doType(VK_C);
                break;
            case 'd':
                doType(VK_D);
                break;
            case 'e':
                doType(VK_E);
                break;
            case 'f':
                doType(VK_F);
                break;
            case 'g':
                doType(VK_G);
                break;
            case 'h':
                doType(VK_H);
                break;
            case 'i':
                doType(VK_I);
                break;
            case 'j':
                doType(VK_J);
                break;
            case 'k':
                doType(VK_K);
                break;
            case 'l':
                doType(VK_L);
                break;
            case 'm':
                doType(VK_M);
                break;
            case 'n':
                doType(VK_N);
                break;
            case 'o':
                doType(VK_O);
                break;
            case 'p':
                doType(VK_P);
                break;
            case 'q':
                doType(VK_Q);
                break;
            case 'r':
                doType(VK_R);
                break;
            case 's':
                doType(VK_S);
                break;
            case 't':
                doType(VK_T);
                break;
            case 'u':
                doType(VK_U);
                break;
            case 'v':
                doType(VK_V);
                break;
            case 'w':
                doType(VK_W);
                break;
            case 'x':
                doType(VK_X);
                break;
            case 'y':
                doType(VK_Y);
                break;
            case 'z':
                doType(VK_Z);
                break;
            case 'A':
                doType(VK_SHIFT, VK_A);
                break;
            case 'B':
                doType(VK_SHIFT, VK_B);
                break;
            case 'C':
                doType(VK_SHIFT, VK_C);
                break;
            case 'D':
                doType(VK_SHIFT, VK_D);
                break;
            case 'E':
                doType(VK_SHIFT, VK_E);
                break;
            case 'F':
                doType(VK_SHIFT, VK_F);
                break;
            case 'G':
                doType(VK_SHIFT, VK_G);
                break;
            case 'H':
                doType(VK_SHIFT, VK_H);
                break;
            case 'I':
                doType(VK_SHIFT, VK_I);
                break;
            case 'J':
                doType(VK_SHIFT, VK_J);
                break;
            case 'K':
                doType(VK_SHIFT, VK_K);
                break;
            case 'L':
                doType(VK_SHIFT, VK_L);
                break;
            case 'M':
                doType(VK_SHIFT, VK_M);
                break;
            case 'N':
                doType(VK_SHIFT, VK_N);
                break;
            case 'O':
                doType(VK_SHIFT, VK_O);
                break;
            case 'P':
                doType(VK_SHIFT, VK_P);
                break;
            case 'Q':
                doType(VK_SHIFT, VK_Q);
                break;
            case 'R':
                doType(VK_SHIFT, VK_R);
                break;
            case 'S':
                doType(VK_SHIFT, VK_S);
                break;
            case 'T':
                doType(VK_SHIFT, VK_T);
                break;
            case 'U':
                doType(VK_SHIFT, VK_U);
                break;
            case 'V':
                doType(VK_SHIFT, VK_V);
                break;
            case 'W':
                doType(VK_SHIFT, VK_W);
                break;
            case 'X':
                doType(VK_SHIFT, VK_X);
                break;
            case 'Y':
                doType(VK_SHIFT, VK_Y);
                break;
            case 'Z':
                doType(VK_SHIFT, VK_Z);
                break;
            case '`':
                doType(VK_BACK_QUOTE);
                break;
            case '0':
                doType(VK_0);
                break;
            case '1':
                doType(VK_1);
                break;
            case '2':
                doType(VK_2);
                break;
            case '3':
                doType(VK_3);
                break;
            case '4':
                doType(VK_4);
                break;
            case '5':
                doType(VK_5);
                break;
            case '6':
                doType(VK_6);
                break;
            case '7':
                doType(VK_7);
                break;
            case '8':
                doType(VK_8);
                break;
            case '9':
                doType(VK_9);
                break;
            case '-':
                doType(VK_MINUS);
                break;
            case '=':
                doType(VK_EQUALS);
                break;
            case '~':
                doType(VK_SHIFT, VK_BACK_QUOTE);
                break;
            case '!':
                doType(VK_EXCLAMATION_MARK);
                break;
            case '@':
                doType(VK_SHIFT, VK_2);
                break;
            case '#':
                doType(VK_SHIFT, VK_3);
                break;
            case '$':
                doType(VK_DOLLAR);
                break;
            case '%':
                doType(VK_SHIFT, VK_5);
                break;
            case '^':
                doType(VK_CIRCUMFLEX);
                break;
            case '&':
                doType(VK_AMPERSAND);
                break;
            case '*':
                doType(VK_ASTERISK);
                break;
            case '(':
                doType(VK_LEFT_PARENTHESIS);
                break;
            case ')':
                doType(VK_RIGHT_PARENTHESIS);
                break;
            case '_':
                doType(VK_UNDERSCORE);
                break;
            case '+':
                doType(VK_PLUS);
                break;
            case '\t':
                doType(VK_TAB);
                break;
            case '\n':
                doType(VK_ENTER);
                break;
            case '[':
                doType(VK_OPEN_BRACKET);
                break;
            case ']':
                doType(VK_CLOSE_BRACKET);
                break;
            case '\\':
                doType(VK_BACK_SLASH);
                break;
            case '{':
                doType(VK_SHIFT, VK_OPEN_BRACKET);
                break;
            case '}':
                doType(VK_SHIFT, VK_CLOSE_BRACKET);
                break;
            case '|':
                doType(VK_SHIFT, VK_BACK_SLASH);
                break;
            case ';':
                doType(VK_SEMICOLON);
                break;
            case ':':
                doType(VK_COLON);
                break;
            case '\'':
                doType(VK_QUOTE);
                break;
            case '"':
                doType(VK_QUOTEDBL);
                break;
            case ',':
                doType(VK_COMMA);
                break;
            case '<':
                doType(VK_SHIFT, VK_COMMA);
                break;
            case '.':
                doType(VK_PERIOD);
                break;
            case '>':
                doType(VK_SHIFT, VK_PERIOD);
                break;
            case '/':
                doType(VK_SLASH);
                break;
            case '?':
                doType(VK_SHIFT, VK_SLASH);
                break;
            case ' ':
                doType(VK_SPACE);
                break;
            default:
                throw new IllegalArgumentException("Cannot type character " + character);
        }
    }

    private static void doType(int... keyCodes)
    {
        doType(keyCodes, 0, keyCodes.length);
    }

    private static void doType(int[] keyCodes, int offset, int length)
    {
        if (length == 0)
        {
            return;
        }

        robot.keyPress(keyCodes[offset]);
        doType(keyCodes, offset + 1, length - 1);
        robot.keyRelease(keyCodes[offset]);
    }
}
