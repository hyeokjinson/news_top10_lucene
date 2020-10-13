package com.saltlux.edu.dor;

import com.saltlux.dor.api.IN2SimSearcher;
import com.saltlux.dor.api.common.DORRunable;

public class SimSearchTest {
	public static void simSearchByInnerDoc(){
		IN2SimSearcher simSearcher=new IN2SimSearcher();
		
		simSearcher.newQuery();
		simSearcher.setServer("127.0.0.1", 10000);
		simSearcher.addIndex("NEWS");
		
		simSearcher.setCentralDoc("NEWS","_id","6796881140386169473",false);
		simSearcher.addSimFieldWeight("TMS_SIMILARITY",1.0f);
		simSearcher.setMinScore(20);
		simSearcher.addReturnField(new String[] {"[SCORE]","TITLE"});
		simSearcher.setReturnPositionCount(0,10);
		
		if(simSearcher.searchDocument()) {
			System.out.println(simSearcher.getTotalDocumentCount());
			long current=simSearcher.getDocumentCount();
			for (int i=0; i<current; i++) {
				System.out.println(simSearcher.getValueInDocument(i, "TITLE")+"\t"+
						simSearcher.getValueInDocument(i,"[SCORE]"));
				
				
			}	
		}else {
			System.out.println(simSearcher.getLastErrorMessage());
		}
	}
	public static void simSearchByOuterDoc() {
		IN2SimSearcher simSearcher=new IN2SimSearcher();
		simSearcher.newQuery();
		simSearcher.setServer("127.0.0.1", 10000);
		simSearcher.addIndex("NEWS");
		String[] sources = {"[사진=하남감일 한양수자인 투시도]\r\n" +
		        "㈜한양이 22일 ‘하남감일 한양수자인’의 사이버 모델하우스를 열고 본격적인 분양몰이에 나선다.\r\n" +
		        "한양은 코로나19에 대한 재확산 우려를 감안, 사이버 모델하우스로 견본주택 현장 운영을 대체한다.\r\n" +
		        "청약안내와 입지·단지, 세대 안내 영상과 VR 등 상세 정보를 제공, 모델하우스를 방문하지 않고도 충분한 정보를 얻을 수 있도록 지원한다.\r\n" +
		        "견본주택 관람은 청약 당첨자 발표 이후 당첨자와 직계가족 1인에 한해 허용될 예정이다.\r\n" +
		        "이날 사이버 모델하우스를 오픈한 하남감일 한양수자인은 내달 2일 특별공급 접수를 시작, 3일 1순위 청약이 진행된다. 평당분양가는 1786만원 수준으로 공급되며, 전매는 입주자 선정일로부터 5년까지 금지된다.\r\n" +
		        "하남감일 한양수자인은 경기 하남시 감일 택지개발지구 B2BL 일원에 송파 생활권과 인접한 위치와 공원, 상업지구 등 다양한 주거 인프라를 갖춘 총 512가구의 단지다.\r\n" +
		        "지하 1층~지상 29층 높이의 총 5개 동으로 구성했고 전세대가 전용면적 84㎡ 단일 평형으로 공급되며, 단지 남측에는 경관녹지를 포함하는 역사문화공원이 조성된다.\r\n" +
		        "상업시설, 교육 등 하남감일 택지지구의 프리미엄을 가장 중심에서 누릴 수 있는 입지적 장점과 송파, 강남 등 서울 중심지와의 양호한 접근성을 갖췄다.\r\n" +
		        "이 단지는 약 1만 3000세대 공급을 앞두고 있는 하남감일 택지지구의 중심에 위치하고 있는데다 단지 인근에 형성되는 상업시설은 지구 내 중심상업지구를 형성할 것으로 기대를 모으고 있다.\r\n" +
		        "특히, 단지와 인접한 서하남IC를 통해 서울외곽순환도로를 이용하면 강남으로의 접근이 용이하고, 강동대로를 통해 송파생활권의 인프라도 편리하게 이용할 수 있다.\r\n" +
		        "아울러 하남감일 한양수자인 입주민들의 주거 만족도를 높이기 위한 다양한 주거시스템과 특화설계도 돋보인다.\r\n" +
		        ""};
		String[] options= {DORRunable.FEATURE_DEFAULT};
		
		simSearcher.addSimFieldWeight("TMS_SIMILARITY",1.0f);
		simSearcher.addCentralField("TMS_SIMILARITY","KOR",sources,options,30);
		simSearcher.setMinScore(20);
		simSearcher.addReturnField(new String[] {"[SCORE]","TITLE"});
		simSearcher.setReturnPositionCount(0,10);
		
		if(simSearcher.searchDocument()) {
			System.out.println(simSearcher.getTotalDocumentCount());
			long current=simSearcher.getDocumentCount();
			for (int i=0; i<current; i++) {
				System.out.println(simSearcher.getValueInDocument(i, "TITLE")+"\t"+simSearcher.getValueInDocument(i,"[SCORE]"));
				
				
			}
			
		}
		else {
			System.out.println(simSearcher.getLastErrorMessage());
		}
	}
	public static void main(String[] args) throws Exception{
		simSearchByInnerDoc();
		simSearchByOuterDoc();
	}
}
