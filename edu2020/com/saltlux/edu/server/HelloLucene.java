package com.saltlux.edu.server;

import java.util.Map;
import java.util.List;

import com.google.gson.Gson;
import com.saltlux.edu.lucene.SearchIndex;
import com.saltlux.nrpc.AbstractHandler;
import com.saltlux.nrpc.AbstractHandlers;
import com.saltlux.nrpc.AbstractResponser;

import io.netty.handler.codec.http.HttpMethod;

public class HelloLucene extends AbstractHandler<InputParams, OutputParams> {

  private final Gson gson;
  public HelloLucene() {
    gson = new Gson();
  }

//127.0.0.1:8888/lucene?field=TITLE&value=오늘&sort=DATE&sortvalue=asc&max=10

  @Override
  protected InputParams unmarshalRESTRequest(HttpMethod method, String uriPath, Map<String, String> uriParams,
      Map<String, List<String>> queryParams, String httpContent) throws Exception {
    InputParams input = new InputParams();
    if (method == HttpMethod.POST) {
      input = gson.fromJson(httpContent, InputParams.class);
      return input;
    } else if (method == HttpMethod.GET) {
      try {
      List<String> plist = queryParams.get("field");
      input.field = plist.get(0);
      plist = queryParams.get("value");
      input.value = plist.get(0);
      plist = queryParams.get("sort");
      input.sortField = plist.get(0);
      plist = queryParams.get("sortvalue");
      input.sortValue = plist.get(0);
      plist = queryParams.get("max");
      input.max = Integer.parseInt(plist.get(0));
      }catch (Exception e) {
			e.printStackTrace();	
		}
      return input;
      }
    return input;
    }

  

  
  @Override
  protected void doExecute(InputParams reqeust, AbstractResponser<OutputParams> responser) {
    //결과 반환 부분 <-- 작업!!
	  SearchIndex searcher = new SearchIndex();
	  OutputParams output = searcher.search(reqeust);
	  responser.onResponse(output);
  }
  @Override
  protected void registerHandler(AbstractHandlers handlers) {
    handlers.registerRESTHandler(HttpMethod.GET, "/lucene", this);
    handlers.registerRESTHandler(HttpMethod.POST, "/lucene", this);
  }

}
