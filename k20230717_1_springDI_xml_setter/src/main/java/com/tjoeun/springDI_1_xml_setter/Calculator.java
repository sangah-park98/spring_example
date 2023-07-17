package com.tjoeun.springDI_1_xml_setter;

public class Calculator {

   public void add(int firstNum, int secondNum) {
      System.out.println("add");
      int result=firstNum + secondNum;
      System.out.println(firstNum+"+"+secondNum+"+"+result);
   }
   public void sub(int firstNum, int secondNum) {
      System.out.println("add");
      int result=firstNum - secondNum;
      System.out.println(firstNum+"-"+secondNum+"+"+result);
   }
   public void mul(int firstNum, int secondNum) {
      System.out.println("mul");
      int result=firstNum * secondNum;
      System.out.println(firstNum+"*"+secondNum+"+"+result);
   }
   public void div(int firstNum, int secondNum) {
      System.out.println("div");
      int result=firstNum / secondNum;
      System.out.println(firstNum+"/"+secondNum+"+"+result);
   }

}