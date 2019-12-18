package io.smallbird.common.utils;

import org.apache.shiro.session.Session;

import java.io.*;
import java.util.Base64;
 
/**
 * 序列和反序列Session对象，只有将session对象序列化成字符串，才可以存储到Mysql上，不能直接存
 */
public class SerializableUtils {
 
 
    /**
     * 将Session序列化成String类型
     * @param session
     * @return
     */
    public static String serializ(Session session) {
        try {
            //ByteArrayOutputStream 用于存储序列化的Session对象
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
 
            //将Object对象输出成byte数据
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(session);
 
            //将字节码，编码成String类型数据
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("序列化失败");
        }
    }
 
 
    /**
     * 将一个Session的字符串序列化成字符串,反序列化
     *
     * @param sessionStr
     * @return
     */
    public static Session deserializ(String sessionStr) {
        try {
            //读取字节码表
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(sessionStr));
 
            //将字节码反序列化成 对象
            ObjectInputStream in = new ObjectInputStream(bis);
            Session session = (Session) in.readObject();
            return session;
        } catch (Exception e) {
            System.out.println("sessionStr:" + sessionStr);
            throw new RuntimeException("反序列化失败");
        }
    }

//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        //读取字节码表
//        ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode("rO0ABXNyACpvcmcuYXBhY2hlLnNoaXJvLnNlc3Npb24ubWd0LlNpbXBsZVNlc3Npb26dHKG41YxibgMAAHhwdwIA23QAJDljZTRlNjhkLWNiZjItNGFkZC1iMjY5LWNmNjNlMjYyYjVmOHNyAA5qYXZhLnV0aWwuRGF0ZWhqgQFLWXQZAwAAeHB3CAAAAWrQ5rONeHNxAH4AA3cIAAABatEHKVB4dxMAAAAAJAyEAAAJMTI3LjAuMC4xc3IAEWphdmEudXRpbC5IYXNoTWFwBQfawcMWYNEDAAJGAApsb2FkRmFjdG9ySQAJdGhyZXNob2xkeHA/QAAAAAAADHcIAAAAEAAAAAN0ABFzaGlyb1NhdmVkUmVxdWVzdHNyACZvcmcuYXBhY2hlLnNoaXJvLndlYi51dGlsLlNhdmVkUmVxdWVzdK/OPK15gsq6AgADTAAGbWV0aG9kdAASTGphdmEvbGFuZy9TdHJpbmc7TAALcXVlcnlTdHJpbmdxAH4ACkwACnJlcXVlc3RVUklxAH4ACnhwdAAEUE9TVHB0AB8veWlmdS1hZG1pbi93eC9zaG9wVHJvbGxleS9saXN0dABQb3JnLmFwYWNoZS5zaGlyby5zdWJqZWN0LnN1cHBvcnQuRGVmYXVsdFN1YmplY3RDb250ZXh0X0FVVEhFTlRJQ0FURURfU0VTU0lPTl9LRVlzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABdABNb3JnLmFwYWNoZS5zaGlyby5zdWJqZWN0LnN1cHBvcnQuRGVmYXVsdFN1YmplY3RDb250ZXh0X1BSSU5DSVBBTFNfU0VTU0lPTl9LRVlzcgAyb3JnLmFwYWNoZS5zaGlyby5zdWJqZWN0LlNpbXBsZVByaW5jaXBhbENvbGxlY3Rpb26of1glxqMISgMAAUwAD3JlYWxtUHJpbmNpcGFsc3QAD0xqYXZhL3V0aWwvTWFwO3hwc3IAF2phdmEudXRpbC5MaW5rZWRIYXNoTWFwNMBOXBBswPsCAAFaAAthY2Nlc3NPcmRlcnhxAH4ABj9AAAAAAAAMdwgAAAAQAAAAAXQACzE1OTUxNjUwMjU1c3IAF2phdmEudXRpbC5MaW5rZWRIYXNoU2V02GzXWpXdKh4CAAB4cgARamF2YS51dGlsLkhhc2hTZXS6RIWVlri3NAMAAHhwdwwAAAAQP0AAAAAAAAFzcgAhaW8ueWlmdS5tb2R1bGVzLnBjLmVudGl0eS5TeXNVc2VyAAAAAAAAAAECABZMAAthdWRpdFN0YXR1c3QAE0xqYXZhL2xhbmcvSW50ZWdlcjtMABJidXNpbmVzc0xpY2Vuc2VQaWNxAH4ACkwAC2NvbXBhbnlOYW1lcQB+AApMAApjb25maXJtUHdkcQB+AApMAApjcmVhdGVUaW1ldAAQTGphdmEvdXRpbC9EYXRlO0wABmRlcHRJZHQAEExqYXZhL2xhbmcvTG9uZztMAAhkZXB0TmFtZXEAfgAKTAAFZW1haWxxAH4ACkwAFGlkZW50aXR5Q2FyZHNCYWNrUGljcQB+AApMABVpZGVudGl0eUNhcmRzRnJvbnRQaWNxAH4ACkwACGludGVncmFscQB+ABxMAAlsb2dpblR5cGVxAH4ACkwACHBhc3N3b3JkcQB+AApMAAhyZWFsTmFtZXEAfgAKTAAKcm9sZUlkTGlzdHQAEExqYXZhL3V0aWwvTGlzdDtMAARzYWx0cQB+AApMAAZzdGF0dXNxAH4AHEwADHRlbGVwaG9uZU51bXEAfgAKTAAGdXNlcklkcQB+AB5MAAh1c2VyVHlwZXEAfgAKTAAIdXNlcm5hbWVxAH4ACkwAEHZlcmlmaWNhdGlvbkNvZGVxAH4ACnhwc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAABwcHBzcQB+AAN3CAAAAWqMYi8weHBwcHBwcQB+ACN0AAx0ZWxlcGhvbmVOdW1wdAALMTU5NTE2NTAyNTVwcHNxAH4AIQAAAAFxAH4AF3NyAA5qYXZhLmxhbmcuTG9uZzuL5JDMjyPfAgABSgAFdmFsdWV4cQB+ACIAAAAAAAABYHQABHNob3B0AAsxNTk1MTY1MDI1NXB4eAB3AQFxAH4AFnh4eA=="));
//        //将字节码反序列化成 对象
//        ObjectInputStream in = new ObjectInputStream(bis);
//        Object o = in.readObject();
//        System.out.println(o);
//    }
}