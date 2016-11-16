package com.nj.xufeng.xfutils.https;

import com.nj.xufeng.xfutils.https.calback.ResultCalback;

import java.util.Map;


public class HttpMoreManage {
	private int maxNum = 0;
	private int num = 0;
	private ResultCalback mCompleteCalback;
	private CompleteListener completeListener;
	public HttpMoreManage(){
	}
	
	public synchronized void addNum(){
		num++;
		if(maxNum==num){
			if(null!=mCompleteCalback)
			mCompleteCalback.onComplete();
			if(null!=completeListener)
			completeListener.onComplete();
		}
	}

	public void setOnCompleteListener(CompleteListener completeListener) {
		this.completeListener = completeListener;
	}

	public void setCompleteCalback(ResultCalback completeCalback){
		this.mCompleteCalback = completeCalback;
	}


	public interface CompleteListener{
		public void onComplete();
	}

	public class PullScrollRequestCallBack implements ResultCalback<Map<String, Object>> {

		public PullScrollRequestCallBack reset(){
			return this;
		}
		
		public PullScrollRequestCallBack append(){
			return this;
		}

		@Override
		public void onHeadleSuccess(Map<String, Object> stringObjectMap) {


		}

		@Override
		public void onHeadleFailure(Map<String, Object> stringObjectMap) {
		}

		@Override
		public void onSuccess(Map<String, Object> t) {
		}

		@Override
		public void onFailure(int errCode, String errMsg, Exception e) {
		}

		@Override
		public void onComplete() {
			addNum();
		}


		@Override
		public void onCancelled() {
		}

		@Override
		public void onLoading(long total, long current, boolean isUploading) {
		}

		@Override
		public void onStart() {
		}

	}

	private PullScrollRequestCallBack requestCallBack = new PullScrollRequestCallBack();

	public PullScrollRequestCallBack getRequestCallBack() {
		maxNum++;
		return requestCallBack;
	}
	
}
