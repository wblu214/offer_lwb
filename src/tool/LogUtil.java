//package tool;
//
//import lombok.Value;
//import lombok.extern.slf4j.Slf4j;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Slf4j
//@Component
//public class LogUtils {
//        /**
//         * 默认日志保存路径
//         */
//        public static String LOG_DIR_PATH;
//
//
//
//        /**
//         * 日志写入方法
//         * @param logFilePath 日志路径
//         * @param logs        写入内容
//         */
//
//        private static void writeLogs(Path logFilePath, String... logs) {
//
//            // 对文件地址加锁
//
//            synchronized (logFilePath.toString().intern()) {
//
//                try {
//                    // 创建保存路径
//
//                    Files.createDirectories(logFilePath.getParent());
//
//                    // 写入日志数据(替换换行符)
//
//                    Files.write(logFilePath, Arrays.stream(logs)
//
//                            .map(text -> text.replaceAll("[\\r\\n]", ""))
//
//                            .collect(Collectors.toList()), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//
//                    log.info("成功写入{}条日志,到{}", logs.length, logFilePath);
//
//                } catch (Exception e) {
//                    log.error("日志" + logFilePath + "记录失败", e);
//                }
//            }
//        }
//
//        /**
//         * 写入日志方法
//         * @param logFileName 日志名称
//         * @param logs        需要写入的日志
//         */
//
//        public static void writeLogs(String logFileName, String... logs) {
//
//            writeLogs(Paths.get(LOG_DIR_PATH + File.separator + logFileName + ".logx"), logs);
//
//        }
//
//
//
//        /**
//         * 写入日志方法
//         * @param logFileName 日志名称
//         * @param logs        需要写入的日志
//         */
//
//        public static void writeLogs(String logFileName, List<String> logs) {
//
//            writeLogs(logFileName, logs.toArray(new String[]{}));
//
//        }
//
//
//
//        /**
//         * 获取日志文件未知
//         * @param logFileName 日志名称
//         * @return 日志路径
//         */
//
//        public static String getLogPath(String logFileName) {
//
//            return Paths.get(LOG_DIR_PATH + File.separator + logFileName + ".logx").toString();
//
//        }
//
//
//
//        /**
//         * 读取日志文件
//         * @param logFilePath 日志路径
//         * @return 日志内容
//         */
//
//        private static List<String> readLogs(Path logFilePath) {
//
//            // 对文件地址加锁
//
//            synchronized (logFilePath.toString().intern()) {
//
//                try {
//
//                    if (!"logx".equals(FilenameUtils.getExtension(logFilePath.toString()))) {
//                        throw new RuntimeException(String.format("错误的日志文件格式:%s,请检查文件拓展名.", FilenameUtils.getExtension(logFilePath.toString())));
//                    }
//
//                    log.info("开始从日志文件{},读取日志", logFilePath);
//                    try (Stream<String> lineStream = Files.lines(logFilePath, StandardCharsets.UTF_8)) {
//
//                        return lineStream
//                                .parallel()
//                                .collect(Collectors.toList());
//
//                    }
//
//                } catch (Exception e) {
//                    log.error("日志" + logFilePath + "读取失败", e);
//                    return new ArrayList<>();
//
//                }
//
//            }
//
//        }
//
//
//
//        /**
//         * 流式读取日志文件
//         * @param logFilePath 日志文件路径
//         * @param batchSize   分批数量
//         * @param linesOp     分批操作
//         */
//
//        public static void readLogs(Path logFilePath, int batchSize, LinesOp linesOp) {
//
//            // 对文件地址加锁
//
//            synchronized (logFilePath.toString().intern()) {
//
//                try {
//                    if (!"logx".equals(FilenameUtils.getExtension(logFilePath.toString()))) {
//
//                        throw new RuntimeException(String.format("错误的日志文件格式:%s,请检查文件拓展名.", FilenameUtils.getExtension(logFilePath.toString())));
//
//                    }
//
//                    log.info("开始从日志文件{},读取日志", logFilePath);
//                    List<String> bufferList = new ArrayList<>();
//                    AtomicInteger counter = new AtomicInteger(0);
//                    LineIterator lineIterator = FileUtils.lineIterator(logFilePath.toFile());
//
//                    while (lineIterator.hasNext()) {
//
//                        bufferList.add(lineIterator.nextLine());
//
//                        if (batchSize == bufferList.size()) {
//
//                            linesOp.linesOp(bufferList);
//
//                            log.info("日志:{},已读取:{}", logFilePath, counter.addAndGet(bufferList.size()));
//
//                            bufferList.clear();
//
//                        }
//
//                    }
//
//                    linesOp.linesOp(bufferList);
//
//                    log.info("日志:{}处理完毕,累计读取:{}", logFilePath, counter.addAndGet(bufferList.size()));
//
//                    lineIterator.close();
//
//                } catch (Exception e) {
//
//                    log.error("日志" + logFilePath + "读取失败", e);
//
//                }
//
//            }
//
//        }
//
//
//
//        /**
//         * 读取日志内容
//         * @param logFilePath 日志文件路径
//         * @return 日志内容
//         */
//
//        public static List<String> readLogs(String logFilePath) {
//
//            return readLogs(Paths.get(logFilePath));
//
//        }
//
//
//
//        /**
//         * 读取日志内容
//         * @param logFilePath 日志文件路径
//
//         * @param batchSize   分批数量
//
//         * @param linesOp     每批次操作
//
//         */
//
//        public static void readLogs(String logFilePath, int batchSize, LinesOp linesOp) {
//            readLogs(Paths.get(logFilePath), batchSize, linesOp);
//
//        }
//
//
//
//        /**
//
//         * 删除日志文件
//
//         *
//
//         * @param logFilePath 日志文件路径
//
//         */
//
//        private static void deleteLogs(Path logFilePath) {
//
//            // 对文件地址加锁
//            synchronized (logFilePath.toString().intern()) {
//                try {
//                    if (!"logx".equals(FilenameUtils.getExtension(logFilePath.toString()))) {
//                        throw new RuntimeException(String.format("错误的日志文件格式:%s,请检查文件拓展名.", FilenameUtils.getExtension(logFilePath.toString())));
//                    }
//
//                    log.info("删除日志文件{}", logFilePath);
//
//                    Files.delete(logFilePath);
//
//                } catch (Exception e) {
//                    log.error("日志" + logFilePath + "删除失败", e);
//                }
//            }
//        }
//
//
//
//        /**
//         * 删除日志文件
//         * @param logFilePath 日志文件路径
//         */
//
//        public static void deleteLogs(String logFilePath) {
//            deleteLogs(Paths.get(logFilePath));
//        }
//
//
//
//        @Value(value = "${basic-service.log-dir-path:./logs}")
//        public void setLogDirPath(String logDirPath) {
//            LOG_DIR_PATH = logDirPath;
//        }
//
//
//
//        @FunctionalInterface
//        public interface LinesOp {
//            /**
//             * 批处理函数
//             * @param lines 待处理的list
//             */
//
//            void linesOp(List<String> lines) throws Exception;
//
//        }
//}
