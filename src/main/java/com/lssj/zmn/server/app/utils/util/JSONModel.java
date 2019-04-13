package com.lssj.zmn.server.app.utils.util;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultDefaultValueProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.*;

public class JSONModel {

    private static final Logger logger = LoggerFactory.getLogger(JSONModel.class);
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    protected static final Class[] NUMBER_CLASSES = new Class[]{int.class, Integer.class, Byte.class, byte.class, Short.class, short.class, Long.class, long.class, Float.class, float.class, Double.class, double.class};
    /**
     * JSON configuration
     */
    protected JsonConfig jsonConfig = new JsonConfig();
    /**
     * JSON processor
     */
    protected static NumberJsonValueProcessor numberProcessor = new NumberJsonValueProcessor();
    protected static NullValueProcessor nullProcessor = new NullValueProcessor();
    protected DateJsonValueProcessor dateProcessor = new DateJsonValueProcessor();
    protected BigDecimalJsonValueProcessor bigDecimalProcessor = new BigDecimalJsonValueProcessor();
    protected List<String> excludesProperties = new ArrayList<String>();
    /**
     * The base model for output.
     */
    protected BaseModel baseModel = new BaseModel();

    private JSONModel() {
        init();
    }

    private void init() {
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        /*
         * register value processor
         */
        //register Date value processor
        jsonConfig.registerJsonValueProcessor(Date.class, dateProcessor);
        //register BigDecimal value processor
        jsonConfig.registerJsonValueProcessor(BigDecimal.class, bigDecimalProcessor);
        //register number value processor
        for (Class clazz : NUMBER_CLASSES) {
            jsonConfig.registerJsonValueProcessor(clazz, numberProcessor);
            //register default null value processor
            if (!clazz.isPrimitive()) {
                jsonConfig.registerDefaultValueProcessor(clazz, nullProcessor);
            }
        }
        //register string null value processor
        jsonConfig.registerDefaultValueProcessor(String.class, nullProcessor);

        //Set excludes properties
        String[] excludes = new String[excludesProperties.size()];
        jsonConfig.setExcludes(excludesProperties.toArray(excludes));
    }

    /**
     * Create JSONModel instance with status of "success" and errorMessage "".
     *
     * @param data The data
     * @return Return JSONModel
     */
    public static JSONModel create(Object data) {
        JSONModel model = new JSONModel();
        model.setData(data);
        return model;
    }

    /**
     * Create JSONModel instance from a JSON String.
     *
     * @param content The JSON String
     * @return Return the JSONModel
     */
    public static JSONModel fromString(String content) {
        JSONObject jsonObject = JSONObject.fromObject(content);
        JSONModel object = (JSONModel) JSONObject.toBean(jsonObject, JSONModel.class);
        return object;
    }

    /**
     * Create JSONModel instance with status of "biz" and errorMessage.
     *
     * @param errorMessage The error message
     * @return Return JSONModel
     */
    public static JSONModel createBizErrorModel(String errorMessage) {
        JSONModel model = new JSONModel();
        model.setErrorMessage(errorMessage);
        model.setStatus(STATUS.BIZERROR);
        return model;
    }

    public static JSONModel createFormErrorModel(String name, String errorMessage) {
        JSONModel model = new JSONModel();
        model.setData(name);
        model.setStatus(STATUS.FORMERROR);
        model.setErrorMessage(errorMessage);
        return model;
    }

    /**
     * Create JSONModel instance with status of "systemError" and errorMessage.
     *
     * @param errorMessage The error message
     * @return Return JSONModel
     */
    public static JSONModel createSystemErrorModel(String errorMessage) {
        JSONModel model = new JSONModel();
        model.setErrorMessage(errorMessage);
        model.setStatus(STATUS.SYSERROR);
        return model;
    }

    /**
     * Create JSONModel instance with status of "authorityRequired" and errorMessage.
     *
     * @param errorMessage The error message
     * @return Return JSONModel
     */
    public static JSONModel createAuthModel(String errorMessage) {
        JSONModel model = new JSONModel();
        model.setErrorMessage(errorMessage);
        model.setStatus(STATUS.AUTH_KEY);
        return model;
    }

    /**
     * Create JSONModel instance with status of "cache" and empty errorMessage.
     *
     * @return Return JSONModel
     */
    public static JSONModel createCacheModel() {
        JSONModel model = new JSONModel();
        model.setStatus(STATUS.CACHEED);
        return model;
    }

    public Object getData() {
        return this.baseModel.getData();
    }

    public JSONModel setData(Object data) {
        this.baseModel.setData(data);
        return this;
    }

    public String getErrorMessage() {
        return this.baseModel.getErrorMessage();
    }

    public JSONModel setErrorMessage(String errorMessage) {
        this.baseModel.setErrorMessage(errorMessage);
        return this;
    }

    public STATUS getStatus() {
        return this.baseModel.getStatus();
    }

    public JSONModel setStatus(STATUS status) {
        this.baseModel.setStatus(status);
        return this;
    }

    /**
     * Set excludes properties.
     *
     * @param properties The array of properties name
     * @return Return JSONModel
     */
    public JSONModel setExcludes(String[] properties) {
        this.excludesProperties.addAll(Arrays.asList(properties));
        return this;
    }

    /**
     * Add excludes properties.
     *
     * @param properties The array of properties name
     * @return Return JSONModel
     */
    public JSONModel addExcludes(String[] properties) {
        this.excludesProperties.addAll(Arrays.asList(properties));
        return this;
    }

    /**
     * Remove excludes properties.
     *
     * @param property The array of properties name
     * @return Return JSONModel
     */
    public JSONModel removeExcludes(String property) {
        this.excludesProperties.remove(property);
        return this;
    }

    /**
     * Set date format pattern.
     *
     * @param pattern The pattern
     * @return Return JSONModel
     */
    public JSONModel setDatePattern(String pattern) {
        dateProcessor.setDatePattern(pattern);
        return this;
    }

    /**
     * Set date format locale.
     *
     * @param locale The date locale
     * @return Return JSONModel
     */
    public JSONModel setDateLocale(Locale locale) {
        dateProcessor.setLocale(locale);
        return this;
    }

    /**
     * Set whether use default datetime pattern.
     *
     * @param isDatetime
     * @return Return JSONModel
     */
    public JSONModel setDatePatternDatetime(boolean isDatetime) {
        dateProcessor.setDatetime(isDatetime);
        return this;
    }

    /**
     * Set BigDecimal format pattern.
     *
     * @param pattern The pattern
     * @return Return JSONModel
     */
    public JSONModel setBigDecimalPattern(String pattern) {
        bigDecimalProcessor.setPattern(pattern);
        return this;
    }

    /**
     * Return the JSON String.
     *
     * @return Return the JSON String
     */
    @Override
    public String toString() {
        StringWriter writer = new StringWriter();
        this.write(writer);
        return writer.toString();
    }

    /**
     * Write the JSON String to the writer.
     *
     * @param writer The writer
     */
    public void write(Writer writer) {
        JSON json = JSONSerializer.toJSON(baseModel.toMap(), jsonConfig);
        json.write(writer);
    }

    public static String convertObjectToJSON(Object object) {
        JSON json = JSONSerializer.toJSON(object);
        StringWriter writer = new StringWriter();
        json.write(writer);
        return writer.toString();
    }


    /**
     * Convert the data object to a bean.
     *
     * @param toClass         The root to class.
     * @param nestedToClasses The nested convert to classes, (key, value) with (fieldName, fieldClass)
     * @param <T>             The root convert class type
     * @return Return the bean
     */
    public <T> T dataToBean(Class<T> toClass, Map<String, Class> nestedToClasses) {
        JSONObject jsonObject = JSONObject.fromObject(this.getData(), this.jsonConfig);
        T object = null;
        if (nestedToClasses != null) {
            try {
                object = (T) JSONObject.toBean(jsonObject, toClass, nestedToClasses);
            } catch (Exception exception) {
            }

        } else {
            object = (T) JSONObject.toBean(jsonObject, toClass);
        }
        return object;
    }

    /**
     * The Status for model.
     */
    public static enum STATUS {
        SUCCESS,
        SYSERROR,
        BIZERROR,
        FORMERROR,
        AUTH_KEY,
        CACHEED;
    }

    /**
     * The base model for output.
     */
    public final class BaseModel {

        private Object data;
        private STATUS status = STATUS.SUCCESS;
        private String errorMessage;
        private Map<String, Object> more;

        public Object getData() {
            if (data == null) {
                return new ArrayList<Object>();
            }
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public STATUS getStatus() {
            return status;
        }

        public void setStatus(STATUS status) {
            this.status = status;
        }

        public Map<String, Object> getMore() {
            return more;
        }

        public synchronized void addMore(String key, Object value) {
            if (more == null) {
                more = new HashMap<String, Object>();
            }
            more.put(key, value);
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", getData());
            map.put("status", status);
            map.put("errorMessage", errorMessage == null ? "" : errorMessage);
            if (more != null && !more.isEmpty()) {
                map.putAll(more);
            }
            return map;
        }
    }

    /**
     * Json Value process for java.util.Date.
     */
    final class DateJsonValueProcessor implements JsonValueProcessor {

        static final String DEFAULT_DATE_PATTERN = "MM/dd/yyyy";
        static final String DEFAULT_DATETIME_PATTERN = "MM/dd/yyyy hh:mm:ss";
        private Locale locale = Locale.US;
        private String datePattern;

        public DateJsonValueProcessor() {
            this.datePattern = DEFAULT_DATE_PATTERN;

        }

        public DateJsonValueProcessor(String datePattern) {
            this.datePattern = datePattern;
        }

        public DateJsonValueProcessor(boolean isDatetime) {
            if (isDatetime) {
                this.datePattern = DEFAULT_DATETIME_PATTERN;
            } else {
                this.datePattern = DEFAULT_DATE_PATTERN;
            }
        }

        public void setDatePattern(String datePattern) {
            this.datePattern = datePattern;
        }

        public void setDatetime(boolean isDatetime) {
            this.datePattern = DEFAULT_DATETIME_PATTERN;
        }

        public void setLocale(Locale locale) {
            this.locale = locale;
        }

        public Object processArrayValue(Object value, JsonConfig jsonConfig) {
            return process(value);
        }

        public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
            return process(value);
        }

        private Object process(Object value) {
            return value == null ? "" : FormatUtil.formatDate(datePattern, (Date) value, locale);
        }
    }

    /**
     * BigDecimal Json value processor.
     */
    final class BigDecimalJsonValueProcessor implements JsonValueProcessor {

        static final String DEFAULT_PATTERN = "##0.00";
        private String pattern;

        public BigDecimalJsonValueProcessor() {
            this.pattern = DEFAULT_PATTERN;

        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public BigDecimalJsonValueProcessor(String pattern) {
            this.pattern = pattern;
        }

        public Object processArrayValue(Object value, JsonConfig jsonConfig) {
            return process(value);
        }

        public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
            return process(value);
        }

        private Object process(Object value) {
            return value == null ? null : FormatUtil.formatNumber(pattern, ((BigDecimal) value).doubleValue());
        }
    }

    /**
     * Number json value processor, convert number to string.
     */
    final static class NumberJsonValueProcessor implements JsonValueProcessor {

        public NumberJsonValueProcessor() {
        }

        public Object processArrayValue(Object value, JsonConfig jsonConfig) {
            return process(value);
        }

        public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
            return process(value);
        }

        private Object process(Object value) {
            return value == null ? null : String.valueOf(value);
        }
    }

    /**
     * NULL value processor.
     */
    final static class NullValueProcessor extends DefaultDefaultValueProcessor {

        @Override
        public Object getDefaultValue(Class type) {
            return "";
        }
    }

}
