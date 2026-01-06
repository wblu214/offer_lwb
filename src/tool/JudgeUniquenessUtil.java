//package tool;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.lang.reflect.Field;
//import java.util.*;
//
//@Slf4j
//public class JudgeUniquenessUtil {
//    public static <T> List<T> judgeUniqueness(List<T> judgeList, String... fields) {
//
//        return judgeUniqueness(judgeList, null, fields);
//
//    }
//
//    /**
//     * 判断唯一性并去重，加入优先级
//     *
//     * @param judgeList          需判断唯一性的对象List
//     * @param priorityComparator 优先级Comparator定义
//     * @param fields             唯一性字段完整的属性名
//     * @return 去重后的List
//     */
//
//    //具体示例详见USBisFuseServiceTest测试类[可参考比较器传参]
//    public static <T> List<T> judgeUniqueness(List<T> judgeList, Comparator<T> priorityComparator, String... fields) {
//
//        Map<String, T> uniqueMap = new LinkedHashMap<>();
//
//        if (judgeList == null || judgeList.isEmpty()) {
//            return new ArrayList<>();
//
//        }
//
//        for (T t : judgeList) {
//
//            StringBuilder unitedField = new StringBuilder();
//            boolean valid = true;
//            for (String f : fields) {
//
//                Field field;
//                try {
//                    field = t.getClass().getDeclaredField(f);
//                    field.setAccessible(true);
//                    String uniquenessField = String.valueOf(field.get(t)).replaceAll("[\\r\\n\\t\\s]", "");
//                    unitedField.append(uniquenessField);
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    log.error("反射访问类异常：{}:{}", f, e.getMessage());
//                    valid = false;
//                    break;
//                }
//            }
//            if (valid) {
//                String key = unitedField.toString();
//                T existing = uniqueMap.get(key);
//                if (existing == null) {
//                    uniqueMap.put(key, t);
//                    //如果传入了优先级,按照优先级取值
//                } else if (priorityComparator != null) {
//                    if (priorityComparator.compare(t, existing) > 0) {
//                        uniqueMap.put(key, t);
//                    }
//                }
//            }
//        }
//        return new ArrayList<>(uniqueMap.values());
//    }
//}
