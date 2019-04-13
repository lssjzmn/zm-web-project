package com.lssj.zmn.server.app.utils.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lancec on 2014/8/27.
 */
public class TemplateUtil {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(TemplateUtil.class);
    /**
     * The relative path in classpath.
     */
    private static String velocityPath = "/templates";
    /**
     * The cached VelocityEngine.
     */
    private static VelocityEngine velocityEngine = null;

    private static Map<String, Template> cachedTemplates = new HashMap<String, Template>();

    /**
     * 获取模板内容。
     *
     * @param templateFile 模板文件名称，基于${classpath}/templates
     * @param model        数据模型
     * @return 返回处理后的内容
     */
    public static String getContent(String templateFile, Map<String, Object> model) {
        try {
            VelocityContext context = new VelocityContext(model);
            Template template = getTemplate(templateFile);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer.toString();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    private static synchronized Template getTemplate(String fileName) throws Exception {
        Template template = cachedTemplates.get(fileName);
        if (template == null) {
            template = getVelocityEngine().getTemplate(fileName);
            cachedTemplates.put(fileName, template);
        }
        return template;
    }

    /**
     * Get the velocity engine,if not exist in cache,create it.
     *
     * @return Return the velocity engine
     */
    private static synchronized VelocityEngine getVelocityEngine() {
        if (velocityEngine == null) {
            velocityEngine = createVelocityEngine();
        }
        return velocityEngine;
    }

    /**
     * Create a velocity engine using FileResourceClassloader.
     *
     * @return Return the velocity engine
     */
    private static VelocityEngine createVelocityEngine() {
        Properties properties = new Properties();
        //get the absolute file path of velocityPath
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        logger.debug("Velocity ClassPath: " + path);
        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path + velocityPath);
        String encoding = "UTF-8";
        properties.setProperty(Velocity.ENCODING_DEFAULT, encoding);
        properties.setProperty(Velocity.INPUT_ENCODING, encoding);
        properties.setProperty(Velocity.OUTPUT_ENCODING, encoding);
        try {
            VelocityEngine engine = new VelocityEngine();
            engine.init(properties);
            return engine;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
