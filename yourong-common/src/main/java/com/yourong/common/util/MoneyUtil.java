package com.yourong.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

/***
 * 货币单位转换
 * 
 */
public class MoneyUtil {

	private static final int WAN = 10000;
	/** 大写数字 */
	private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍",
			"陆", "柒", "捌", "玖" };
	/** 整数部分的单位 */
	private static final String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰",
			"仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };
	/** 小数部分的单位 */
	private static final String[] DUNIT = { "角", "分", "厘" };

	/**
	 * 将分转换成元
	 * 
	 * @param cent
	 * @return
	 */
	public static BigDecimal convertToYuan(Long cent) {
		if (cent == null)
			return null;

		return new BigDecimal(cent).movePointLeft(2);
	}

	/**
	 * 将分转换成元
	 * 
	 * @param cent
	 * @return
	 */
	public static String convertToYuanString(Long cent) {
		if (cent == null)
			return null;

		BigDecimal b = new BigDecimal(cent).movePointLeft(2);
		return b.toString();
	}

	/**
	 * 将double元转换成BigDecimal元
	 * 
	 * @param cent
	 * @return
	 */
	public static BigDecimal convertDoubleToYuanString(double yuan) {
		return convertToYuan(convertToCent(yuan));
	}

	/**
	 * 由元转换成分
	 * 
	 * @param yuan
	 * @return
	 */
	public static long convertToCent(double yuan) {
		return new Money(yuan).getCent();
	}

	public static long convertToCent(String yuan) {
		if (!StringUtils.isEmpty(yuan)) {
			return convertToCent(Double.valueOf(yuan));
		} else {
			return 0L;
		}

	}

	/**
	 * 将分转换成元（主要给页面用）
	 * 
	 * @param cent
	 * @return
	 */
	public static Money getMoney(long cent) {
		return new Money(cent);
	}

	/**
	 * 将分（字符串）转换成元（主要给页面用）
	 * 
	 * @param cent
	 * @return
	 */
	public static Money getMoneyByString(String cent) {
		if (cent == null) {
			return new Money(0, 0);
		}

		Long centl = Long.parseLong(cent);
		return new Money(centl);

	}

	/**
	 * 得到大写金额。
	 */
	public static String toChinese(String str) {
		str = str.replaceAll(",", "");// 去掉","
		String integerStr;// 整数部分数字
		String decimalStr;// 小数部分数字

		// 初始化：分离整数部分和小数部分
		if (str.indexOf(".") > 0) {
			integerStr = str.substring(0, str.indexOf("."));
			decimalStr = str.substring(str.indexOf(".") + 1);
		} else if (str.indexOf(".") == 0) {
			integerStr = "";
			decimalStr = str.substring(1);
		} else {
			integerStr = str;
			decimalStr = "";
		}
		// integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
		if (!integerStr.equals("")) {
			integerStr = Long.toString(Long.parseLong(integerStr));
			if (integerStr.equals("0")) {
				integerStr = "";
			}
		}
		// overflow超出处理能力，直接返回
		if (integerStr.length() > IUNIT.length) {
			System.out.println(str + ":超出处理能力");
			return str;
		}

		int[] integers = toArray(integerStr);// 整数部分数字
		boolean isMust5 = isMust5(integerStr);// 设置万单位
		int[] decimals = toArray(decimalStr);// 小数部分数字
		return getChineseInteger(integers, isMust5)
				+ getChineseDecimal(decimals);
	}

	/**
	 * 整数部分和小数部分转换为数组，从高位至低位
	 */
	private static int[] toArray(String number) {
		int[] array = new int[number.length()];
		for (int i = 0; i < number.length(); i++) {
			array[i] = Integer.parseInt(number.substring(i, i + 1));
		}
		return array;
	}

	/**
	 * 得到中文金额的整数部分。
	 */
	private static String getChineseInteger(int[] integers, boolean isMust5) {
		StringBuffer chineseInteger = new StringBuffer("");
		int length = integers.length;
		for (int i = 0; i < length; i++) {
			// 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)
			// 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元)
			String key = "";
			if (integers[i] == 0) {
				if ((length - i) == 13)// 万(亿)(必填)
					key = IUNIT[4];
				else if ((length - i) == 9)// 亿(必填)
					key = IUNIT[8];
				else if ((length - i) == 5 && isMust5)// 万(不必填)
					key = IUNIT[4];
				else if ((length - i) == 1)// 元(必填)
					key = IUNIT[0];
				// 0遇非0时补零，不包含最后一位
				if ((length - i) > 1 && integers[i + 1] != 0)
					key += NUMBERS[0];
			}
			chineseInteger.append(integers[i] == 0 ? key
					: (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
		}
		return chineseInteger.toString();
	}

	/**
	 * 得到中文金额的小数部分。
	 */
	private static String getChineseDecimal(int[] decimals) {
		StringBuffer chineseDecimal = new StringBuffer("");
		for (int i = 0; i < decimals.length; i++) {
			// 舍去3位小数之后的
			if (i == 3)
				break;
			chineseDecimal.append(decimals[i] == 0 ? ""
					: (NUMBERS[decimals[i]] + DUNIT[i]));
		}
		return chineseDecimal.toString();
	}

	/**
	 * 判断第5位数字的单位"万"是否应加。
	 */
	private static boolean isMust5(String integerStr) {
		int length = integerStr.length();
		if (length > 4) {
			String subInteger = "";
			if (length > 8) {
				// 取得从低位数，第5到第8位的字串
				subInteger = integerStr.substring(length - 8, length - 4);
			} else {
				subInteger = integerStr.substring(0, length - 4);
			}
			return Integer.parseInt(subInteger) > 0;
		} else {
			return false;
		}
	}

	/**
	 * 格式化为万，不做四舍五入1
	 * @param amount
	 * @return
	 */
	public static String formatToWan(BigDecimal amount){
		if(amount==null){
			return "0";
		}
		BigDecimal wanAmount = amount.divide(new BigDecimal(WAN));
		return new DecimalFormat("######.##########").format(wanAmount);
	}
	/**
	 * 格式化为万，不做四舍五入1
	 * @param amount
	 * @return
	 */
	public static String convertNumberToWAN(String amount){
		if(amount==null){
			return "0万";
		}
		
		BigDecimal wanAmount = new BigDecimal(amount).divide(new BigDecimal(WAN));
		return new DecimalFormat("######.##########").format(wanAmount)+"万";
	}
}