package io.smallbird.common.utils;

import java.security.SecureRandom;
import java.util.Random;
 
public class RandomCharsUtils {
    
    private static final String SYMBOLS_NUM = "0123456789"; // 数字
 
    private static final String SYMBOLS_CHAR_NUM = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
 
    private static final Random RANDOM = new SecureRandom();
	
	public static String getSixNum() {
		return getSymbols(SymbolsType.SYMBOLS_NUM, 6);
	}

	public static String getSymbols(SymbolsType symbolsType,int times){
		char[] nonceChars = new char[times];
		for (int index = 0; index < nonceChars.length; ++index) {
			nonceChars[index] = symbolsType.getStr().charAt(RANDOM.nextInt(symbolsType.getStr().length()));
		}
		return new String(nonceChars);
	}

	public enum SymbolsType{
		SYMBOLS_NUM(0,"0123456789"),
		SYMBOLS_CHAR_NUM(1,"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

		private int idx;
		private String str;

		private SymbolsType(int idx,String str){
			this.idx = idx;
			this.str = str;
		}

		public int getIdx() {
			return idx;
		}

		public void setIdx(int idx) {
			this.idx = idx;
		}

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}
	}

	public static void main(String[] args) {
		System.out.println(getSixNum());
	}
}