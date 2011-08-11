package application.tests;

import java.net.*;
import java.io.*;
import java.util.*;
class HttpURLDemo
{
public static void main(String args[]) throws Exception {
URL hp = new URL("http://www.google.com");
HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();
// Отображение метода запроса.
System.out.println("Метод запроса: " + hpCon.getRequestMethod());
// Отображение кода ответа.
System.out.println("Код ответа: " + hpCon.getResponseCode());
// Отображение сообщения ответа.
System.out.println("Сообщение ответа: " + hpCon.getResponseMessage());
// Получить список полей заголовка и набор его ключей.
Map<String, List<String>> hdrMap = hpCon.getHeaderFields();
Set<String> hdrField = hdrMap.keySet();
System.out.println("\nЗдесь следует заголовок:");
// Отобразить все ключи и значения заголовка.
for(String k : hdrField) {
System.out.println("Ключ: " + k + " Значение: " + hdrMap.get(k));
}
}
}