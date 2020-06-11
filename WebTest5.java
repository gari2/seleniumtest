import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class WebTest5{

	 /**
	   * 全角数字を半角に変換します。
	   * @param s 変換元文字列
	   * @return 変換後文字列
	   */
	  public static String zenkakuNumToHankaku(String s) {
	    StringBuffer sb = new StringBuffer(s);
	    for (int i = 0; i < sb.length(); i++) {
	      char c = sb.charAt(i);
	      if (c >= '０' && c <= '９') {
	        sb.setCharAt(i, (char)(c - '０' + '0'));
	      }
	    }
	    return sb.toString();
	  }
		  public static String hankakuNumberToZenkakuNumber(String s) {
		    StringBuffer sb = new StringBuffer(s);
		    for (int i = 0; i < s.length(); i++) {
		      char c = s.charAt(i);
		      if (c >= '0' && c <= '9') {
		        sb.setCharAt(i, (char) (c - '0' + '０'));
		      }
		    }
		    return sb.toString();
		  }

    private static final void kensakugamen(WebDriver driver, String title) {
    if ( driver.findElements(By.linkText(title)).size() > 0) {
    	   //リンクテキスト名が"入力されたタイトル"の要素を取得
        WebElement element3 = driver.findElement(By.linkText(title));
        //画像のリンクをクリック
        element3.click();
    }else if(driver.findElements(By.linkText("次")).size() > 0) {
        WebElement element7 = driver.findElement(By.linkText("次"));
        element7.click();
        kensakugamen(driver, title);
    }else {
    	System.out.println("対象の作品はありませんでした。");
    }
    }

    private static void search(WebDriver driver, String title, int i) {
        //指定したURLに遷移する
        driver.get("https://www.google.co.jp");
        driver.get("https://store-tsutaya.tsite.jp/?sc_ext=tsutaya_store_header_search");

        //要素をid番号から取得する(idが001のセレクトタグ)
        WebElement element = driver.findElement(By.name("i"));
        //指定するテキストを設定する
        String text = "レンタルコミック/商品名";
        //セレクトタグの要素を指定してSelectクラスのインスタンスを作成
        Select select = new Select(element);
        //セレクトタグのオプションをテキストを指定して選択する
        select.selectByVisibleText(text);

        //検索テキストボックスの要素をClass属性名から取得
        WebElement element2 = driver.findElement(By.className("search_keyword-input"));
        //検索テキストボックスに"Selenium"を入力
        element2.sendKeys(title);
        //Enterキーを押下して検索を実行
        element2.sendKeys(Keys.ENTER);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        kensakugamen(driver, title);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //リンクが"https://store-tsutaya.tsite.jp"を含む要素を取得
//        WebElement element4 = driver.findElement(By.partialLinkText("store-tsutaya.tsite.jp"));
        WebElement element4 = driver.findElement(By.xpath("//*[@id='pbBlock6564280']/div/div/div[4]/div/a"));

        java.lang.System.out.println(element4);
        element4.click();
        //検索テキストボックスの要素をClass属性名から取得
        WebElement element5 = driver.findElement(By.name("SearchKey1"));
        //検索テキストボックスに"Selenium"を入力
        element5.sendKeys("綱島店");
        //Enterキーを押下して検索を実行
        element5.sendKeys(Keys.ENTER);

        WebElement element6 = driver.findElement(By.className("zaiko_btn"));
        element6.click();

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,250)", "");

        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
        	Integer ii = new Integer(i);
        	String si = ii.toString();
        	StringBuilder sb = new StringBuilder();
        	sb.append("./screenshot/shreenshot");
        	sb.append(si);
        	sb.append(".png");
    		FileUtils.copyFile(scrFile, new File(sb.toString()));
    	} catch (IOException e) {
    		// TODO 自動生成された catch ブロック
    		e.printStackTrace();
    	}
    }

  public static void main(String[] args){
	//第一引数に　検索したいマンガのタイトル名、第二引数に何巻までの
	//在庫を確認したいかを半角数字で入力する。

	//Chrome制御のためChromeDriverのパスを指定
    System.setProperty("webdriver.chrome.driver",
    		"./exe/chromedriver.exe");
    //Chromeを起動する
    WebDriver driver = new ChromeDriver();

    String a = args[0];
    int limit = Integer.parseInt(args[1]);

    for(int i=1;i<limit;i++)
    {
    	String str = a+hankakuNumberToZenkakuNumber(String.valueOf(i));
    	System.out.println(str);
    	search(driver, str, i);
    }

  }
}