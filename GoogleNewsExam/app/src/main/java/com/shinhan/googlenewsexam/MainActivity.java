package com.shinhan.googlenewsexam;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    private static String rssUrl =
                            "http://api.sbs.co.kr/xml/news/rss.jsp?pmDiv=entertainment";
    class RSSNewsItem {
        String title; String link; String description; String pubDate;
        String author; String category;
        RSSNewsItem(String title, String link, String description, String pubDate,
                String author, String category) {
            this.title = title; this.link = link; this.description = description;
            this.pubDate = pubDate; this.author = author; this.category = category;
        }
    }
    ArrayList<RSSNewsItem> newsItemList = new ArrayList<RSSNewsItem>(); // 동적배열
    RSSListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsItemList.add(new RSSNewsItem("제목1", "https://m.naver.com",
                "설명", "날짜", "작성자", "카테고리"));
        newsItemList.add(new RSSNewsItem("제목2", "https://m.naver.com",
                "설명", "날짜", "작성자", "카테고리"));
        newsItemList.add(new RSSNewsItem("제목3", "https://m.naver.com",
                "설명", "날짜", "작성자", "카테고리"));
        ListView listView = (ListView)findViewById(R.id.listview);      // listView에 나타나는 데이타는 여러개이다.
        // set or get을 사용하지 않는다.
        listAdapter = new RSSListAdapter(MainActivity.this);
        listView.setAdapter(listAdapter);

        EditText editText = (EditText)findViewById(R.id.input01);
        editText.setText(rssUrl);
    }


    class RSSListAdapter extends ArrayAdapter {

        public RSSListAdapter(Context context) {
            super(context, R.layout.listitem, newsItemList);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            /*  xml을 parsing해가지고.. server에서 가져온 것을 list view에 뿌려준다. */
            if(convertView != null) {
                /* list view 는 한번에 만들지 않는다. 이전에 만들어 놓은 객체가 있다는 의미이다. */
                view = convertView;
            } else {
                // setcontentsview 역할을 하는 것이다
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                view = inflater.inflate(R.layout.listitem, null, true);
            }
            /* view. 에 주의 activity layyout에서는 dataItem01 */
            TextView dataItem01 = (TextView)view.findViewById(R.id.dataItem01);
            TextView dataItem02 = (TextView)view.findViewById(R.id.dataItem02);
            TextView dataItem03 = (TextView)view.findViewById(R.id.dataItem03);
            WebView dataItem04 = (WebView)view.findViewById(R.id.dataItem04);

            dataItem01.setText(newsItemList.get(position).title);
            dataItem02.setText(newsItemList.get(position).pubDate);
            dataItem03.setText(newsItemList.get(position).category);
            //dataItem04.loadUrl(newsItemList.get(position).link);
            dataItem03.setTextColor(Color.RED);

            dataItem04.loadData(newsItemList.get(position).description, "text/html; charset=utf-8", "utf-8");

            return view;
        }
    }

    public void onButtonClicked(View view) {//로딩창화면보이기
        EditText editText = (EditText)findViewById(R.id.input01);
        String urlString=editText.getText().toString();
        if(urlString.indexOf("http")!=-1) {//http라는 문자열이 포함되어 있는지 확인
            new LoadXML().execute(urlString); //입력한 url에 접속
        }
    }

    //안드로이드에서 쓰레드를 구성하는 공식 외워라
    class LoadXML extends AsyncTask<String,String,String> {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {//백그라운드 작업 전에 호출 //그냥 외우기
            super.onPreExecute();
            dialog.setMessage("뉴스 RSS 요청 중...");
            dialog.show();//프로그레스 다이얼로그 보여주기
        }

        @Override
        protected void onPostExecute(String s) {//백그라운드 작업 후에 호출  parsing하고 출력하는 부분
            super.onPostExecute(s);
            dialog.dismiss();//프로그레스 다이얼로그 닫기
            //
            //TextView textView=(TextView)findViewById(R.id.txtMsg);
            //textView.setText(s);//서버에서 받아온 HTML문자열을 TextView에 출력
            //
            // http와 통신하는 법은 다 똑같다.. http가 아닌 xml에서 parsing해서 데이타 추출하여
            // 목표를 설정해라..
            //
            // xml parsing 추가

            // ListView 출력력
            listAdapter.notifyDataSetChanged();;
        }
       @Override
        protected String doInBackground(String... params) {//실제 통신이 처리되는 부분
            StringBuilder output = new StringBuilder();
            try{//통신 부분은 반드시 try-catch로 예외처리 해주어야 한다.
                URL url = new URL(params[0]);//전달받은 urlString으로 URL 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if(conn !=null) {
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    int resCode = conn.getResponseCode();
                    /*
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;        while((line = reader.readLine()) !=null) {output.append(line);//한줄식 읽어서 Stringbuilder 객체에 추가}
                    reader.close();
                    */
                    /////////////////////////////////////////////////////////////////////////
                    DocumentBuilderFactory builderFacotry = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = builderFacotry.newDocumentBuilder();
                    InputStream inputStream = conn.getInputStream();

                    Document document = builder.parse(inputStream);     // document 객체로 곧바로 받은 것이다.
                    int count = processDocument(document);             // xml parsing(DOM파서)
                    Log.i("#### count : ", + count +"");
                    // xml parsing하는 부분 (서버에서 가져오는 것을 리스트뷰에 출력이 가능하다 교재 605p)
                    /////////////////////////////////////////////////////////////////////////
                    conn.disconnect();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }

    private int processDocument(Document document) {        // xml 문서 파싱
        int count = 0;

        newsItemList.clear();       // 동적 배열 (리스트 초기화)

        Element documentElement = document.getDocumentElement();

        NodeList nodelist = documentElement.getElementsByTagName("item");

        if ((nodelist != null)  && (nodelist.getLength() > 0)) { // item이 존재하면
            for(int i=0; i < nodelist.getLength(); i++) {        // item 개수만큼 반복
                RSSNewsItem newsItem = dissectNode(nodelist, i);  //아이템정보 추출
                if(newsItem != null) {
                    newsItemList.add(newsItem);     count++;
                }
            }
        }
        return count;
    }

    private RSSNewsItem dissectNode(NodeList nodelist, int index) {     // 특정 아이템 정보 추출
        RSSNewsItem newsItem = null;
        try {
            Element entry = (Element)nodelist.item(index);    //아이템객체 추출
            Element title = (Element)entry.getElementsByTagName("title").item(0);
            Element link = (Element)entry.getElementsByTagName("link").item(0);
            Element description = (Element)entry.getElementsByTagName("description").item(0);
            Element pubDate = (Element)entry.getElementsByTagName("pubDate").item(0);
            Element author = (Element)entry.getElementsByTagName("author").item(0);
            Element category = (Element)entry.getElementsByTagName("category").item(0);

            // element 객체가 아닌 string이 중요하다.
            String titleValuie = getElementString(title);
            String linkValue = getElementString(link);
            String descriptionValue = getElementString(description);
            String pubDateValue = getElementString(pubDate);
            String authorValue = getElementString(author);
            String categoryValue = getElementString(category);

            newsItem = new RSSNewsItem(titleValuie, linkValue, descriptionValue, pubDateValue, authorValue, categoryValue);
        }catch(Exception e) {
            e.printStackTrace();;
        }
        return newsItem;
    }

    private String getElementString(Element element) {  //Element 객체에서 문자열 추출
        String value = "";
        if(element != null) {
            Node firstChild = element.getFirstChild();
            if(firstChild != null)  value = firstChild.getNodeValue();
        }
        return value;
    }
}
