/*
Navicat MySQL Data Transfer

Source Server         : limingyang
Source Server Version : 50731
Source Host           : localhost:3306
Source Database       : eblog

Target Server Type    : MYSQL
Target Server Version : 50731
File Encoding         : 65001

Date: 2020-11-01 21:03:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for m_category
-- ----------------------------
DROP TABLE IF EXISTS `m_category`;
CREATE TABLE `m_category` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '内容描述',
  `summary` text COLLATE utf8mb4_unicode_ci,
  `icon` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
  `post_count` int(11) unsigned DEFAULT '0' COMMENT '该分类的内容数量',
  `order_num` int(11) DEFAULT NULL COMMENT '排序编码',
  `parent_id` bigint(32) unsigned DEFAULT NULL COMMENT '父级分类的ID',
  `meta_keywords` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SEO关键字',
  `meta_description` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SEO描述内容',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `modified` datetime DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_category
-- ----------------------------
INSERT INTO `m_category` VALUES ('1', '提问', null, null, null, '0', null, null, null, null, '2020-04-28 22:36:48', null, '0');
INSERT INTO `m_category` VALUES ('2', '分享', null, null, null, '0', null, null, null, null, '2020-04-28 22:36:48', null, '0');
INSERT INTO `m_category` VALUES ('3', '讨论', null, null, null, '0', null, null, null, null, '2020-04-28 22:36:48', null, '0');
INSERT INTO `m_category` VALUES ('4', '建议', null, null, null, '0', null, null, null, null, '2020-04-28 22:36:48', null, '0');

-- ----------------------------
-- Table structure for m_comment
-- ----------------------------
DROP TABLE IF EXISTS `m_comment`;
CREATE TABLE `m_comment` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论的内容',
  `parent_id` bigint(32) DEFAULT NULL COMMENT '回复的评论ID',
  `post_id` bigint(32) NOT NULL COMMENT '评论的内容ID',
  `user_id` bigint(32) NOT NULL COMMENT '评论的用户ID',
  `vote_up` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '“顶”的数量',
  `vote_down` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '“踩”的数量',
  `level` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '置顶等级',
  `status` tinyint(2) DEFAULT NULL COMMENT '评论的状态',
  `created` datetime NOT NULL COMMENT '评论的时间',
  `modified` datetime DEFAULT NULL COMMENT '评论的更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_comment
-- ----------------------------
INSERT INTO `m_comment` VALUES ('43', '好的好的face[微笑] ', null, '40', '1', '0', '0', '0', null, '2020-10-20 14:09:59', '2020-10-20 14:09:59');
INSERT INTO `m_comment` VALUES ('44', 'face[哈哈] 一定做到', null, '40', '1', '0', '0', '0', null, '2020-10-20 14:10:10', '2020-10-20 14:10:10');
INSERT INTO `m_comment` VALUES ('45', 'face[哈哈] 牛逼呀', null, '34', '1', '0', '0', '0', null, '2020-10-20 14:10:31', '2020-10-20 14:10:31');
INSERT INTO `m_comment` VALUES ('46', '可以呀。老铁face[good] ', null, '35', '1', '0', '0', '0', null, '2020-10-20 14:11:05', '2020-10-20 14:11:05');
INSERT INTO `m_comment` VALUES ('47', '简单得很face[酷] ', null, '37', '1', '0', '0', '0', null, '2020-10-20 14:11:28', '2020-10-20 14:11:28');
INSERT INTO `m_comment` VALUES ('48', 'face[哼] 装', null, '36', '1', '0', '0', '0', null, '2020-10-20 14:11:45', '2020-10-20 14:11:45');
INSERT INTO `m_comment` VALUES ('49', '哈哈', null, '39', '1', '0', '0', '0', null, '2020-10-30 07:01:15', '2020-10-30 07:01:15');
INSERT INTO `m_comment` VALUES ('50', '哈哈face[嘻嘻] ', null, '34', '12', '0', '0', '0', null, '2020-10-30 07:22:19', '2020-10-30 07:22:19');
INSERT INTO `m_comment` VALUES ('51', 'face[微笑] ', null, '41', '12', '0', '0', '0', null, '2020-10-30 07:23:30', '2020-10-30 07:23:30');

-- ----------------------------
-- Table structure for m_post
-- ----------------------------
DROP TABLE IF EXISTS `m_post`;
CREATE TABLE `m_post` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `edit_mode` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '编辑模式：html可视化，markdown ..',
  `category_id` bigint(32) DEFAULT NULL,
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户ID',
  `vote_up` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '支持人数',
  `vote_down` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '反对人数',
  `view_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '访问量',
  `comment_count` int(11) NOT NULL DEFAULT '0' COMMENT '评论数量',
  `recommend` tinyint(1) DEFAULT NULL COMMENT '是否为精华',
  `level` tinyint(2) NOT NULL DEFAULT '0' COMMENT '置顶等级',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `modified` datetime DEFAULT NULL COMMENT '最后更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_post
-- ----------------------------
INSERT INTO `m_post` VALUES ('34', '【笔记】本周热议功能实现', '本周热议功能实现实例\n环境准备\n\nSpringBoot\nRedis\nMySQL\n思路\n\n将前天内发表的文章的评论数量存入Redis，利用Redis中ZSet数据结构的特性，将七天内的博客的评论数存入Redis，然后求并集，通过score的大小顺序来确定热议。\n\n测试：\n\n\n[pre]\n## 每天的评论数量存入数据库 格式： zdd day:时间 数量 post:文章ID\n127.0.0.1:6379> zadd day:18 10 post:1\n(integer) 1\n127.0.0.1:6379> zadd day:19 10 post:1\n(integer) 1\n127.0.0.1:6379> zadd day:20 10 post:1\n(integer) 1\n127.0.0.1:6379> zadd day:18 6 post:2\n(integer) 1\n127.0.0.1:6379> zadd day:19 6 post:2\n(integer) 1\n127.0.0.1:6379> zadd day:20 6 post:2\n(integer) 1\n127.0.0.1:6379> keys *\n1) \"day:19\"\n2) \"cart:uid:32\"\n3) \"day:20\"\n4) \"day:18\"\n127.0.0.1:6379> zrevrange day:18 0 -1 withscores\n1) \"post:1\"\n2) \"10\"\n3) \"post:2\"\n4) \"6\"\n## 求并集\n127.0.0.1:6379> zunionstore week:rank 3 day:18 day:19 day:20\n(integer) 2\n127.0.0.1:6379> keys *\n1) \"day:19\"\n2) \"day:18\"\n3) \"cart:uid:32\"\n4) \"week:rank\"\n5) \"day:20\"\n127.0.0.1:6379> zrevrange week:rank 0 -1 withscores\n1) \"post:1\"\n2) \"30\"\n3) \"post:2\"\n4) \"18\"\n127.0.0.1:6379> \n\n\n[/pre]\nSpringBoot 整合Redis实现本周热议步骤\n\n存入Redis并生成并集核心代码：\n\n[pre]\n /**\n     * 本周热议\n     */\n    @Override\n    public void initWeekRank() {\n        // 获取7天内发布的文章\n        List<MPost> posts = this.list(new QueryWrapper<MPost>()\n                .ge(\"created\", DateUtil.offsetDay(new Date(), -7))\n                .select(\"id, title, user_id, comment_count, view_count, created\")\n        );\n\n        // 初始化文章的总评论数\n        for (MPost post : posts) {\n            // 定义ZSet的集合名称: zdd day:rank:20200923   yyyyMMdd\n            String key = \"day:rank:\" + DateUtil.format(post.getCreated(), DatePattern.PURE_DATE_FORMAT);\n\n            redisUtil.zSet(key, post.getId(), post.getCommentCount());\n            // 7天后自动过期\n            long between = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);\n            long expireTime = (7 - between) * 24 * 60 * 60;\n\n            redisUtil.expire(key, expireTime);\n            // 缓存文章的基本信息\n            this.hashCachPost(post, expireTime);\n        }\n\n        // 做并集\n        this.zunionWeekRank();\n\n    }\n\n    // 做并集，合并每日评论的数量\n    private void zunionWeekRank() {\n        // 当前时间的key\n        String currenkey = \"day:rank:\" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);\n        // 并集后保存的集合\n        String destkey = \"week:rank\";\n        List<String> ortherKeys = new ArrayList<>();\n        // 循环添加前七天的key\n        for (int i = -7; i < 0; i++) {\n            String temp = \"day:rank:\" +\n                    DateUtil.format(DateUtil.offsetDay(new Date(), i), DatePattern.PURE_DATE_FORMAT);\n            ortherKeys.add(temp);\n        }\n\n        redisUtil.zUnionAndStore(currenkey, ortherKeys, destkey);\n\n    }\n\n    // 缓存博客的基本信息\n    private void hashCachPost(MPost post, long expireTime) {\n        String key = \"rank:post:\" + post.getId();\n        if(! redisUtil.hasKey(key)) {\n            redisUtil.hset(key, \"post:id\", post.getId(), expireTime);\n            redisUtil.hset(key, \"post:title\", post.getTitle(), expireTime);\n            redisUtil.hset(key, \"post:commentCount\", post.getCommentCount(), expireTime);\n        }\n    }\n\n[/pre]\n获取前7条作为热议：\n\n [pre]\n @Override\n    public List<Map> getHotPost() throws Exception {\n        String weekRankKey = \"week:rank\";\n\n        Set<ZSetOperations.TypedTuple> typedTuples = redisUtil.getZSetRank(weekRankKey, 0, 6);\n\n        List<Map> hotPosts = new ArrayList<>();\n\n        for (ZSetOperations.TypedTuple typedTuple : typedTuples) {\n            Map<String, Object> map = new HashMap<>();\n\n            Object value = typedTuple.getValue(); // post的id\n            String postKey = \"rank:post:\" + value;\n\n            map.put(\"id\", value);\n            map.put(\"title\", redisUtil.hget(postKey, \"post:title\"));\n            map.put(\"commentCount\", typedTuple.getScore());\n\n            hotPosts.add(map);\n        }\n\n        return hotPots;\n\n    }\n\n[/pre]\n', '0', '2', '1', '0', '0', '13', '2', '1', '1', null, '2020-10-06 03:07:24', '2020-10-06 03:07:24');
INSERT INTO `m_post` VALUES ('35', '【笔记】SpringBoot + FreeMarker 将时间处理成几天前、几个月前的格式 ', '将时间处理成几天前的格式\n需求\n\n将时间转化成几天前、几个月前等等！\nimg[https://ferryblog.oss-cn-shenzhen.aliyuncs.com/eblog/45ea4306-d5ee-429a-930d-70ac44543f86.png?x-oss-process=image/resize,h_200] \n\n我的项目环境\n\n主要框架：SpringBoot\n模版引擎：FreeMarker\n解决步骤\n\n首先引入FreeMarker的几个模版工具类\nimg[https://ferryblog.oss-cn-shenzhen.aliyuncs.com/eblog/f772a80b-32e4-494c-9be3-5c6e55965016.png?x-oss-process=image/resize,h_200] \nDirectiveHandler:\n[pre]\npackage com.lmy.eblog.extension.freemarker.templates.common;\n\nimport freemarker.core.Environment;\nimport freemarker.template.*;\nimport org.springframework.util.Assert;\n\nimport java.io.IOException;\nimport java.io.StringWriter;\nimport java.util.Date;\nimport java.util.List;\nimport java.util.Map;\n\n/**\n * 引自mbog项目\n *\n * Created by langhsu on 2017/11/14.\n */\npublic class DirectiveHandler {\n    private Environment env;\n    private Map<String, TemplateModel> parameters;\n    private TemplateModel[] loopVars;\n    private TemplateDirectiveBody body;\n    private Environment.Namespace namespace;\n\n    /**\n     * 构建 DirectiveHandler\n     *\n     * @param env 系统环境变量，通常用它来输出相关内容，如Writer out = env.getOut()。\n     * @param parameters 自定义标签传过来的对象\n     * @param loopVars 循环替代变量\n     * @param body 用于处理自定义标签中的内容，如<@myDirective>将要被处理的内容</@myDirective>；当标签是<@myDirective />格式时，body=null。\n     */\n    public DirectiveHandler(Environment env, Map<String, TemplateModel> parameters, TemplateModel[] loopVars,\n                            TemplateDirectiveBody body) {\n        this.env = env;\n        this.loopVars = loopVars;\n        this.parameters = parameters;\n        this.body = body;\n        this.namespace = env.getCurrentNamespace();\n    }\n\n    public void render() throws IOException, TemplateException {\n        Assert.notNull(body, \"must have template directive body\");\n        body.render(env.getOut());\n    }\n\n    public void renderString(String text) throws Exception {\n        StringWriter writer = new StringWriter();\n        writer.append(text);\n        env.getOut().write(text);\n    }\n\n    public DirectiveHandler put(String key, Object value) throws TemplateModelException {\n        namespace.put(key, wrap(value));\n        return this;\n    }\n\n    public String getString(String name) throws TemplateModelException {\n        return TemplateModelUtils.converString(getModel(name));\n    }\n\n    public Integer getInteger(String name) throws TemplateModelException {\n        return TemplateModelUtils.converInteger(getModel(name));\n    }\n\n    public Short getShort(String name) throws TemplateModelException {\n        return TemplateModelUtils.converShort(getModel(name));\n    }\n\n    public Long getLong(String name) throws TemplateModelException {\n        return TemplateModelUtils.converLong(getModel(name));\n    }\n\n    public Double getDouble(String name) throws TemplateModelException {\n        return TemplateModelUtils.converDouble(getModel(name));\n    }\n\n    public String[] getStringArray(String name) throws TemplateModelException {\n        return TemplateModelUtils.converStringArray(getModel(name));\n    }\n\n    public Boolean getBoolean(String name) throws TemplateModelException {\n        return TemplateModelUtils.converBoolean(getModel(name));\n    }\n\n    public Date getDate(String name) throws TemplateModelException {\n        return TemplateModelUtils.converDate(getModel(name));\n    }\n\n    public String getString(String name, String defaultValue) throws Exception {\n        String result = getString(name);\n        return null == result ? defaultValue : result;\n    }\n\n    public Integer getInteger(String name, int defaultValue) throws Exception {\n        Integer result = getInteger(name);\n        return null == result ? defaultValue : result;\n    }\n\n    public Long getLong(String name, long defaultValue) throws Exception {\n        Long result = getLong(name);\n        return null == result ? defaultValue : result;\n    }\n\n\n    public String getContextPath() {\n        String ret = null;\n        try {\n            ret =  TemplateModelUtils.converString(getEnvModel(\"base\"));\n        } catch (TemplateModelException e) {\n        }\n        return ret;\n    }\n\n    /**\n     * 包装对象\n     * @param object\n     * @return\n     * @throws TemplateModelException\n     */\n    public TemplateModel wrap(Object object) throws TemplateModelException {\n        return env.getObjectWrapper().wrap(object);\n    }\n\n    /**\n     * 获取局部变量\n     * @param name\n     * @return\n     * @throws TemplateModelException\n     */\n    public TemplateModel getEnvModel(String name) throws TemplateModelException {\n        return env.getVariable(name);\n    }\n\n    public void write(String text) throws IOException {\n        env.getOut().write(text);\n    }\n\n    private TemplateModel getModel(String name) {\n        return parameters.get(name);\n    }\n\n    /**\n     * Created by langhsu on 2017/11/14.\n     */\n    public abstract static class BaseMethod implements TemplateMethodModelEx {\n\n        public String getString(List<TemplateModel> arguments, int index) throws TemplateModelException {\n            return TemplateModelUtils.converString(getModel(arguments, index));\n        }\n\n        public Integer getInteger(List<TemplateModel> arguments, int index) throws TemplateModelException {\n            return TemplateModelUtils.converInteger(getModel(arguments, index));\n        }\n\n        public Long getLong(List<TemplateModel> arguments, int index) throws TemplateModelException {\n            return TemplateModelUtils.converLong(getModel(arguments, index));\n        }\n\n        public Date getDate(List<TemplateModel> arguments, int index) throws TemplateModelException {\n            return TemplateModelUtils.converDate(getModel(arguments, index));\n        }\n\n        public TemplateModel getModel(List<TemplateModel> arguments, int index) {\n            if (index < arguments.size()) {\n                return arguments.get(index);\n            }\n            return null;\n        }\n    }\n}\n\n\n[/pre]\n\n\nTemplateDirective:\n\n[pre]\npackage com.lmy.eblog.extension.freemarker.templates.common;\n\nimport freemarker.core.Environment;\nimport freemarker.template.TemplateDirectiveBody;\nimport freemarker.template.TemplateDirectiveModel;\nimport freemarker.template.TemplateException;\nimport freemarker.template.TemplateModel;\n\nimport java.io.IOException;\nimport java.util.Map;\n\n/**\n * Created by langhsu on 2017/11/14.\n */\npublic abstract class TemplateDirective implements TemplateDirectiveModel {\n    protected static String RESULT = \"result\";\n    protected static String RESULTS = \"results\";\n\n    @Override\n    public void execute(Environment env, Map parameters,\n                        TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {\n        try {\n            execute(new DirectiveHandler(env, parameters, loopVars, body));\n        } catch (IOException e) {\n            throw e;\n        } catch (Exception e) {\n            throw new TemplateException(e, env);\n        }\n    }\n\n    abstract public String getName();\n    abstract public void execute(DirectiveHandler handler) throws Exception;\n\n}\n\n\n[/pre]\n\n\nTemplateModelUtils:\n\n\n[pre]\npackage com.lmy.eblog.extension.freemarker.templates.common;\n\nimport freemarker.template.*;\n\nimport java.text.DateFormat;\nimport java.text.ParseException;\nimport java.text.SimpleDateFormat;\nimport java.util.Date;\n\nimport static org.apache.commons.lang3.StringUtils.*;\n\n/**\n * Freemarker 模型工具类\n *\n * Created by langhsu on 2017/11/14.\n */\npublic class TemplateModelUtils {\n\n    public static final DateFormat FULL_DATE_FORMAT = new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\");\n    public static final int FULL_DATE_LENGTH = 19;\n\n    public static final DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat(\"yyyy-MM-dd\");\n    public static final int SHORT_DATE_LENGTH = 10;\n\n    public static String converString(TemplateModel model) throws TemplateModelException {\n        if (null != model) {\n            if (model instanceof TemplateScalarModel) {\n                return ((TemplateScalarModel) model).getAsString();\n            } else if ((model instanceof TemplateNumberModel)) {\n                return ((TemplateNumberModel) model).getAsNumber().toString();\n            }\n        }\n        return null;\n    }\n\n    public static TemplateHashModel converMap(TemplateModel model) throws TemplateModelException {\n        if (null != model) {\n            if (model instanceof TemplateHashModelEx) {\n                return (TemplateHashModelEx) model;\n            } else if (model instanceof TemplateHashModel) {\n                return (TemplateHashModel) model;\n            }\n        }\n        return null;\n    }\n\n    public static Integer converInteger(TemplateModel model) throws TemplateModelException {\n        if (null != model) {\n            if (model instanceof TemplateNumberModel) {\n                return ((TemplateNumberModel) model).getAsNumber().intValue();\n            } else if (model instanceof TemplateScalarModel) {\n                String s = ((TemplateScalarModel) model).getAsString();\n                if (isNotBlank(s)) {\n                    try {\n                        return Integer.parseInt(s);\n                    } catch (NumberFormatException e) {\n                    }\n                }\n            }\n        }\n        return null;\n    }\n\n    public static Short converShort(TemplateModel model) throws TemplateModelException {\n        if (null != model) {\n            if (model instanceof TemplateNumberModel) {\n                return ((TemplateNumberModel) model).getAsNumber().shortValue();\n            } else if (model instanceof TemplateScalarModel) {\n                String s = ((TemplateScalarModel) model).getAsString();\n                if (isNotBlank(s)) {\n                    try {\n                        return Short.parseShort(s);\n                    } catch (NumberFormatException e) {\n                    }\n                }\n            }\n        }\n        return null;\n    }\n\n    public static Long converLong(TemplateModel model) throws TemplateModelException {\n        if (null != model) {\n            if (model instanceof TemplateNumberModel) {\n                return ((TemplateNumberModel) model).getAsNumber().longValue();\n            } else if (model instanceof TemplateScalarModel) {\n                String s = ((TemplateScalarModel) model).getAsString();\n                if (isNotBlank(s)) {\n                    try {\n                        return Long.parseLong(s);\n                    } catch (NumberFormatException e) {\n                    }\n                }\n            }\n        }\n        return null;\n    }\n\n    public static Double converDouble(TemplateModel model) throws TemplateModelException {\n        if (null != model) {\n            if (model instanceof TemplateNumberModel) {\n                return ((TemplateNumberModel) model).getAsNumber().doubleValue();\n            } else if (model instanceof TemplateScalarModel) {\n                String s = ((TemplateScalarModel) model).getAsString();\n                if (isNotBlank(s)) {\n                    try {\n                        return Double.parseDouble(s);\n                    } catch (NumberFormatException ignored) {\n                    }\n                }\n            }\n        }\n        return null;\n    }\n\n    public static String[] converStringArray(TemplateModel model) throws TemplateModelException {\n        if (model instanceof TemplateSequenceModel) {\n            TemplateSequenceModel smodel = (TemplateSequenceModel) model;\n            String[] values = new String[smodel.size()];\n            for (int i = 0; i < smodel.size(); i++) {\n                values[i] = converString(smodel.get(i));\n            }\n            return values;\n        } else {\n            String str = converString(model);\n            if (isNotBlank(str)) {\n                return split(str,\',\');\n            }\n        }\n        return null;\n    }\n\n    public static Boolean converBoolean(TemplateModel model) throws TemplateModelException {\n        if (null != model) {\n            if (model instanceof TemplateBooleanModel) {\n                return ((TemplateBooleanModel) model).getAsBoolean();\n            } else if (model instanceof TemplateNumberModel) {\n                return !(0 == ((TemplateNumberModel) model).getAsNumber().intValue());\n            } else if (model instanceof TemplateScalarModel) {\n                String temp = ((TemplateScalarModel) model).getAsString();\n                if (isNotBlank(temp)) {\n                    return Boolean.valueOf(temp);\n                }\n            }\n        }\n        return null;\n    }\n\n    public static Date converDate(TemplateModel model) throws TemplateModelException {\n        if (null != model) {\n            if (model instanceof TemplateDateModel) {\n                return ((TemplateDateModel) model).getAsDate();\n            } else if (model instanceof TemplateScalarModel) {\n                String temp = trimToEmpty(((TemplateScalarModel) model).getAsString());\n                return parseDate(temp);\n            }\n        }\n        return null;\n    }\n\n    public static Date parseDate(String date) {\n\n        Date ret = null;\n        try {\n            if (FULL_DATE_LENGTH == date.length()) {\n                ret = FULL_DATE_FORMAT.parse(date);\n            } else if (SHORT_DATE_LENGTH == date.length()) {\n                ret = SHORT_DATE_FORMAT.parse(date);\n            }\n        } catch (ParseException e) {\n        }\n        return ret;\n    }\n}\n\n\n[/pre]\n\n\n自定义一个时间处理类并实现 DirectiveHandler.BaseMethod接口\n\nimg[https://img-blog.csdnimg.cn/20201002104538525.png#pic_center] \n\n\n这里的注释已经很详细了，我就不再解释，时间处理的核心逻辑也在这里，大家也可以适当的修改成自己想要的格式，有什么错误的话还请大佬们指出！\n\n\n[pre]\npackage com.lmy.eblog.extension.freemarker.templates;\n/**\n * @Project eblog\n * @Package com.lmy.eblog.extension.freemarker.templates\n * @author Administrator\n * @date 2020/10/2 9:50\n * @version V1.0\n */\n\nimport com.lmy.eblog.extension.freemarker.templates.common.DirectiveHandler;\nimport freemarker.core.Environment;\nimport freemarker.template.*;\nimport org.springframework.stereotype.Component;\n\nimport java.io.IOException;\nimport java.util.Date;\nimport java.util.List;\nimport java.util.Map;\n\n/**\n * @author Lmy\n * @ClassName TimeAgoMethod\n * @Description 自定义时间处理标签\n * @date 2020/10/2 9:50\n **/\n@Component\npublic class TimeAgoMethod extends DirectiveHandler.BaseMethod {\n    // 定义时间单位相应的毫秒数\n    private static final long ONE_MINUTE = 60000L;\n    private static final long ONE_HOUR = 3600000L;\n    private static final long ONE_DAY = 86400000L;\n    private static final long ONE_WEEK = 604800000L;\n\n    // 定义时间后缀\n    private static final String ONE_SECOND_AGO = \"秒前\";\n    private static final String ONE_MINUTE_AGO = \"分钟前\";\n    private static final String ONE_HOUR_AGO = \"小时前\";\n    private static final String ONE_DAY_AGO = \"天前\";\n    private static final String ONE_MONTH_AGO = \"月前\";\n    private static final String ONE_YEAR_AGO = \"年前\";\n    private static final String ONE_UNKNOWN = \"未知\";\n\n    @Override\n    public Object exec(List arguments) throws TemplateModelException {\n        // 获取时间参数\n        Date time = getDate(arguments, 0);\n        // 处理并返回\n        return format(time);\n    }\n\n    /**\n     * 格式化时间\n     * @param date\n     * @return\n     */\n    public static String format(Date date) {\n        // 校验时间是否为空\n        if (null == date) {\n            return ONE_UNKNOWN;\n        }\n        // 将时间转换成毫秒数\n        long delta = new Date().getTime() - date.getTime();\n        // 判断是否小于一分钟\n        if (delta < 1L * ONE_MINUTE) {\n            // 将时间装换成秒\n            long seconds = toSeconds(delta);\n            // 返回秒数加后缀\n            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;\n        }\n\n        if (delta < 60L * ONE_MINUTE) {\n            long minutes = toMinutes(delta);\n            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;\n        }\n        if (delta < 24L * ONE_HOUR) {\n            long hours = toHours(delta);\n            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;\n        }\n        if (delta < 48L * ONE_HOUR) {\n            return \"昨天\";\n        }\n        if (delta < 30L * ONE_DAY) {\n            long days = toDays(delta);\n            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;\n        }\n        if (delta < 12L * 4L * ONE_WEEK) {\n            long months = toMonths(delta);\n            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;\n        } else {\n            long years = toYears(delta);\n            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;\n        }\n    }\n\n    /*----------------- 一系列时间处理方法 -------------------*/\n\n    private static long toSeconds(long date) {\n        return date / 1000L;\n    }\n\n    private static long toMinutes(long date) {\n        return toSeconds(date) / 60L;\n    }\n\n    private static long toHours(long date) {\n        return toMinutes(date) / 60L;\n    }\n\n    private static long toDays(long date) {\n        return toHours(date) / 24L;\n    }\n\n    private static long toMonths(long date) {\n        return toDays(date) / 30L;\n    }\n\n    private static long toYears(long date) {\n        return toMonths(date) / 365L;\n    }\n}\n\n\n[/pre]\n\n\n编写配置类，将自定义的模版注入到FreeMarker环境中\n\n\nimg[https://img-blog.csdnimg.cn/20201002104637513.png#pic_center] \n\n[pre]\npackage com.lmy.eblog.config;\n\nimport com.lmy.eblog.extension.freemarker.templates.TimeAgoMethod;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.context.annotation.Configuration;\n\nimport javax.annotation.PostConstruct;\n\n@Configuration\npublic class FreemarkerConfig {\n\n    @Autowired\n    private freemarker.template.Configuration configuration;\n\n\n    @PostConstruct\n    public void setUp() {\n        configuration.setSharedVariable(\"timeAgo\", new TimeAgoMethod());\n    }\n\n}\n\n\n[/pre]\n\n\n最后直接在页面中使用就好了\n\nimg[https://img-blog.csdnimg.cn/20201002105036305.png#pic_center] ', '0', '2', '1', '0', '0', '14', '1', '0', '0', null, '2020-10-06 03:12:52', '2020-10-06 03:12:52');
INSERT INTO `m_post` VALUES ('36', '从 13K 的前端开源项目我学到了啥？', '近期我们团队的小伙伴小池同学分享了 “BetterScroll 2.0 发布：精益求精，与你同行” 这篇文章到团队内部群，看到了 插件化 的架构设计，阿宝哥突然来了兴趣，因为之前阿宝哥在团队内部也做过相关的分享。既然已经来了兴趣，那就决定开启 BetterScroll 2.0 源码的学习之旅。\n接下来本文的重心将围绕 插件化 的架构设计展开，不过在分析 BetterScroll 2.0 插件化架构之前，我们先来简单了解一下 BetterScroll。\n\nBetterScroll 源码学习脑图 1.0\n\n一、BetterScroll 简介\nBetterScroll 是一款重点解决移动端（已支持 PC）各种滚动场景需求的插件。它的核心是借鉴的 iscroll 的实现，它的 API 设计基本兼容 iscroll，在 iscroll 的基础上又扩展了一些 feature 以及做了一些性能优化。\nBetterScroll 1.0 共发布了 30 多个版本，npm 月下载量 5 万，累计 star 数 12600+。那么为什么升级 2.0 呢？\n\n做 v2 版本的初衷源于社区的一个需求：\n\nBetterScroll 能不能支持按需加载？\n\n来源于：BetterScroll 2.0 发布：精益求精，与你同行\n\n为了支持插件的按需加载，BetterScroll 2.0 采用了 插件化 的架构设计。CoreScroll 作为最小的滚动单元，暴露了丰富的事件以及钩子，其余的功能都由不同的插件来扩展，这样会让 BetterScroll 使用起来更加的灵活，也能适应不同的场景。\n', '0', '1', '12', '0', '0', '13', '1', '0', '1', null, '2020-10-06 03:33:27', '2020-10-06 03:33:27');
INSERT INTO `m_post` VALUES ('37', '自学 Java 怎么入门？', '在大学自学 Java，看视频教程和代码能看懂，自己写就写不出来，怎么样能够入门？face[疑问] ', '0', '3', '12', '0', '0', '14', '1', '0', '0', null, '2020-10-06 03:34:58', '2020-10-06 03:34:58');
INSERT INTO `m_post` VALUES ('38', 'Java自学路线总结，已Get腾讯Offer', '本人19本科应届生，专注Java后台学习，已签腾讯WXG的offer（日后转c++)。\n\n众所周知，鹅厂后台一直以C++为重，面试官也是做C++开发。但是语言只是工具，对代码的理解才是核心。面试时重点考察的是基础知识，以及解题的思考过程。凭借着对Java的理解去回答，也受到了面试官的认可，最终幸运的拿到offer。\n\n一直以来，通过知乎这个程序员大牛聚集地，学习到了很多经验方法，也少走了很多弯路。感谢这个平台给予我的帮助，同时也分享一下我的Java学习经验。\n\nJava基础\n做java开发，java基础是最需要下功夫的一项。在校招时最注重的就是基础，拿不出像样的项目没关系，但是基础万万不可不牢固。\n\n想要基础扎实，看书沉淀是必须的，有一些编程基础的同学推荐阅读《JAVA核心技术 卷1》，可以跳过图形程序设计、事件处理、Swing、applet以及部分日志章节，如果比较吃力也可以先跳过多线程章节。\n看完一本书，一定要多加练习去理解和吸收。科班的同学可以用java写实验，写的同时一定要多多运用学到的特性。练习阶段各种设计模式套上去用，不要怕笨重，即使是滥用特性和设计模式也是一种有效的学习。\n有了一定量的编程经验后，可以再回过头将《JAVA核心技术 卷1》速读一遍，查漏补缺。同时我们开始扩宽我们的知识领域，开始进行JavaWeb的学习。\nJavaWeb基础\nJavaWeb是一系列技术的综合，也是大多数Java学习者日后的技术方向。及早的了解JavaWeb也有利于更深层面理解，Java在完整的应用中，是如何与各个模块交互并发挥作用的。\n\n基础篇包括Servlet和JSP的学习、tomcat的使用、理解MVC分层模式、mysql的基础用法及JDBC、了解http协议。\n\n这部分的学习，我是通过《Head First Servlets and JSP》 。这本书轻松幽默读起来很愉快，但是实在是很厚，而且部分技术已经过时，有时间的同学可以选读。\n可以看到这部分的内容是很杂且多的，此阶段注重广度的基础学习，日后慢慢深入。这里推荐个在线教程：\nServlet系列教材 （一）- 基础 - 教程：开发第一个Servlet - how2j.cn\nmysql系列教材 （一）- 安装mysql-server - how2j.cn\nTomcat系列教材 （一）- 教程 - how2j.cn\nJSP系列教材 （一）- 教程 - how2j.cn\nHTTP协议系列教材 （一）- 教程 - how2j.cn\nJDBC系列教材 （一）- Java 使用JDBC之前，先要准备mysql\nMVC系列教材 （一）- 教程 - how2j.cn\n3. 重点要理解Servlet的原理以及生命周期。在完成这一部分的学习后，可以简单的做个小网站，包括注册登陆，增删改查等功能。如果想继续折腾，可以考虑将项目部署在阿里云或者腾讯云上，一个完整可供他人访问的项目，所获得的成就感是非凡的。\n\n部署到Linux系列教材 （一）- 介绍 - 如何把J2EE应用部署到Linux - how2j.cn\n\nJava进阶\nJava始终是我们JavaWeb开发体系中最核心的一环，唯有不停地探索，才能把握住方向和机遇。\n\n在做完一个简单完整的JavaWeb项目后，我们对代码的认知和理解会提高不少，这对接下来的深入学习打下基础。Java圣经：《JAVA编程思想》 值得仔细品读，作者的功力十分深厚，即使很多内容还无法理解，但每次读完一定会有所收获。同样建议跳过“图形化用户界面“章节，这是一本伴随我们技术成长的好书，买一本放在旁边，摸着就有底气。\n读完编程思想，建议写一个有一定复杂度和代码量的后台项目。可以是一个http服务器，一个大型聊天室，也可以参考我的项目实现一个DBMS：wwwyanxin/wyxDBMS 这一部分要强化我们的Java基础，同时也为日后的招聘积累项目经验。\n做完项目我们又该看书沉淀技术了，此时我推荐《Effective Java》，这本书并不厚但是干货十足，作者讲述Java的最佳实践和经验规则。它能帮助我们写出清晰、健壮、高效的代码，同时这本书涵盖了非常多的面试考点，一定要牢记于心！\n最后还要深入学习Java多线程技术以及Java虚拟机原理，这两部分难度较大，理解起来比较抽象。也许日后工作中，我们很少遇到并发问题，不一定有机会进行JVM调优，但是这部分一定要认真对待，越理解底层写出的代码越高效，查bug时越准确，另外更重要的是面试常考！推荐两本书《Java并发编程的艺术》《深入理解Java虚拟机》，以及并发编程网：并发编程网 - ifeve.com 重点掌握java内存模型，各种锁的原理及应用，JVM GC垃圾回收原理。\nJavaWeb进阶\n围绕整个java体系学习，我们要筑起高高的城墙。\n\nLinux，现在的服务器基本都是Linux系统，也不存在图形化操作界面。作为开发工程师推荐阅读《The Linux Command Line》，有中文在线免费版本TLCL。那本《鸟哥的Linux私房菜》更适合运维工程师，就不推荐给大家了。学习的时候，可以去阿里云或腾讯云租一个学生服务器，每个月大概10元左右，直接ssh到云服务器上操作，能更好地模拟公司的开发及生产环境。\n操作系统原理，主要学习进程控制调度、进程通信、存储和设备管理、文件管理以及系统安全。这一部分可以通过看教材或者自行找一些网课补充。\n数据库，可以买一本《MySQL必知必会》小册子作为基础入门，没有什么理论的堆砌，是一本实践指南。学习数据库原理可以阅读《MySQL技术内幕》索引优化、事务、锁、范式都是重点。\n网络协议，入门可以读《图解HTTP》《图解TCP/IP》如果要深入研究可以读《UNIX网络编程 卷1》和《TCP/IP详解 卷1》大多数开发者接触http和tcp、udp、ip协议比较多，但是对整体网络协议栈有个完整了解是必要的。\n数据结构与算法，数据结构是算法的基础，一定要清晰明了。算法则是笔试面试中无法绕过的难关，推荐去LeetCode刷题，现在也有了中文官方网站：力扣 (LeetCode) 中国官网 - 全球极客挚爱的技术成长平台 从easy难度开始刷起，积累一定题量之后，做算法题会很快找到类型方法。\nSSM框架，Spring+SpringMVC+MyBatis可以说是开发必备了，但框架只是锦上添花，不要太依赖框架进行学习。当我们能不依赖任何框架开发完整项目时，才是真正掌握了它。最为重要的是理解Spring的两个特性：IOC 反转控制和DI 依赖注入。明白实现原理以及为什么要使用Spring，只有这样才能在层出不穷的框架中灵活应对，立于不败之地。\nSpring系列教材 （一）- 教程 - how2j.cn\nSpring MVC系列教材 （一）- 教程\nMybatis系列教材 （一）- 基础 - 入门教程 - how2j.cn\n加分技能\n前端，一些岗位要求全栈，即使不是全栈，懂得前端技术的后台能写出更加合理的接口，与前端工程师合作起来会更顺利，理解项目更通透，解决问题准确迅速。\n学习一门动态语言，动态语言开发起来更灵活迅速。同时比较动态静态语言的特点，可以更好去提炼跳出语言束缚的代码思想。推荐学习JavaScript或者Python等。\n大数据，大数据开发近几年也是热门方向之一，有兴趣的同学可以学习。\n热门工具及框架，包括分布式Dubbo、缓存优化redis、nginx、虚拟化技术docker等。一般来说本科应届生不会要求很高，但是要对热门技术有一定的了解。\n常用网站推荐\nGitHub： Build software better, together\nJava学习：How2J 的 Java教程\nLinux命令行：TLCL\n算法： 力扣 (LeetCode) 中国官网 - 全球极客挚爱的技术成长平台\n正则表达式测试：PHP, PCRE, Python, Golang and JavaScript', '0', '4', '12', '0', '0', '8', '0', '0', '0', null, '2020-10-06 03:35:45', '2020-10-06 03:35:45');
INSERT INTO `m_post` VALUES ('39', '【管理员】 社区公告', '爱问 微信公众号“爱问”已建设完成，并于近期正式上线运行。朋友们可通过搜索“爱问”或扫描舍力提供的二维码（附后）进行关注。\n原文地址：https://www.lmy1218.icu', '0', '2', '11', '0', '0', '10', '1', '1', '1', null, '2020-10-06 03:38:47', '2020-10-06 03:38:47');
INSERT INTO `m_post` VALUES ('40', '【管理员】博客环境净化公告！', '广大博友，您们好！为了大家的发言环境，请大家自觉遵守博客发言规定！face[微笑] face[微笑] ', '0', '4', '11', '0', '0', '22', '2', '1', '1', null, '2020-10-06 03:40:49', '2020-10-06 03:40:49');
INSERT INTO `m_post` VALUES ('41', '测试消息接收', 'face[微笑] ', '0', '1', '1', '0', '0', '3', '1', '0', '0', null, '2020-10-30 07:23:14', '2020-10-30 07:23:14');

-- ----------------------------
-- Table structure for m_user
-- ----------------------------
DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `email` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮件',
  `mobile` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机电话',
  `point` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '积分',
  `sign` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个性签名',
  `gender` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `wechat` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信号',
  `vip_level` int(32) DEFAULT NULL COMMENT 'vip等级',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `avatar` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '头像',
  `post_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '内容数量',
  `comment_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '评论数量',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态',
  `lasted` datetime DEFAULT NULL COMMENT '最后的登陆时间',
  `created` datetime NOT NULL COMMENT '创建日期',
  `modified` datetime DEFAULT NULL COMMENT '最后修改时间',
  `city` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '城市',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO `m_user` VALUES ('1', 'lmy1218', '1d4350430ff0623bda2bd03320383c95', 'lmy@qq.com', '15281890615', '0', '我是一个大佬！!!', '0', '15807902521', '0', '2020-10-03 16:00:00', 'https://ferryblog.oss-cn-shenzhen.aliyuncs.com/eblog/4492d720-48cc-4f40-8c98-7c37824cbe07.jpg?x-oss-process=image/resize,h_200', '11', '4', '0', '2020-10-31 02:50:03', '2020-10-04 12:03:52', '2020-10-03 12:03:52', '四川省达州市');
INSERT INTO `m_user` VALUES ('11', 'admin', '1d4350430ff0623bda2bd03320383c95', 'admin@qq.com', null, '0', null, '0', null, '0', null, '/res/images/avatar/default.png', '0', '0', '0', '2020-10-06 07:21:02', '2020-10-05 11:53:12', '2020-10-05 11:53:12', null);
INSERT INTO `m_user` VALUES ('12', '局外人', '1d4350430ff0623bda2bd03320383c95', '111@qq.com', null, '0', null, '0', null, '0', null, 'https://ferryblog.oss-cn-shenzhen.aliyuncs.com/eblog/508f7854-33a8-44c7-a0ff-54a7069dfb9f.jpg?x-oss-process=image/resize,h_200', '0', '0', '0', '2020-10-30 07:21:32', '2020-10-06 03:30:57', '2020-10-06 03:30:57', null);

-- ----------------------------
-- Table structure for m_user_action
-- ----------------------------
DROP TABLE IF EXISTS `m_user_action`;
CREATE TABLE `m_user_action` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID',
  `action` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '动作类型',
  `point` int(11) DEFAULT NULL COMMENT '得分',
  `post_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的帖子ID',
  `comment_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的评论ID',
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_user_action
-- ----------------------------
INSERT INTO `m_user_action` VALUES ('40', '1', '评论', '0', '40', '43', '2020-10-20 14:09:59', null);
INSERT INTO `m_user_action` VALUES ('41', '1', '评论', '0', '40', '44', '2020-10-20 14:10:10', null);
INSERT INTO `m_user_action` VALUES ('42', '1', '评论', '0', '34', '45', '2020-10-20 14:10:32', null);
INSERT INTO `m_user_action` VALUES ('43', '1', '评论', '0', '35', '46', '2020-10-20 14:11:05', null);
INSERT INTO `m_user_action` VALUES ('44', '1', '评论', '0', '37', '47', '2020-10-20 14:11:28', null);
INSERT INTO `m_user_action` VALUES ('45', '1', '评论', '0', '36', '48', '2020-10-20 14:11:45', null);
INSERT INTO `m_user_action` VALUES ('46', '1', '评论', '0', '39', '49', '2020-10-30 07:01:15', null);
INSERT INTO `m_user_action` VALUES ('47', '12', '评论', '0', '34', '50', '2020-10-30 07:22:19', null);
INSERT INTO `m_user_action` VALUES ('48', '12', '评论', '0', '41', '51', '2020-10-30 07:23:30', null);

-- ----------------------------
-- Table structure for m_user_collection
-- ----------------------------
DROP TABLE IF EXISTS `m_user_collection`;
CREATE TABLE `m_user_collection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  `post_user_id` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_user_collection
-- ----------------------------
INSERT INTO `m_user_collection` VALUES ('32', '1', '36', '12', '2020-10-06 03:42:31', '2020-10-06 03:42:31');

-- ----------------------------
-- Table structure for m_user_message
-- ----------------------------
DROP TABLE IF EXISTS `m_user_message`;
CREATE TABLE `m_user_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_user_id` bigint(20) DEFAULT NULL COMMENT '发送消息的用户ID',
  `to_user_id` bigint(20) NOT NULL COMMENT '接收消息的用户ID',
  `post_id` bigint(20) DEFAULT NULL COMMENT '消息可能关联的帖子',
  `comment_id` bigint(20) DEFAULT NULL COMMENT '消息可能关联的评论',
  `content` text COLLATE utf8mb4_unicode_ci,
  `type` tinyint(2) DEFAULT NULL COMMENT '消息类型',
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_user_message
-- ----------------------------
INSERT INTO `m_user_message` VALUES ('23', '11', '1', '35', '29', '作为管理员，我居然也不会这个！哈哈哈哈face[嘻嘻] ', '1', '2020-10-06 03:41:30', null, '1');
INSERT INTO `m_user_message` VALUES ('24', '11', '12', '37', '30', '从入门到放弃face[偷笑] ', '1', '2020-10-06 03:42:02', null, '1');
INSERT INTO `m_user_message` VALUES ('25', '11', '12', '38', '31', '这么牛逼！face[钱] ', '1', '2020-10-06 03:48:53', null, '1');
INSERT INTO `m_user_message` VALUES ('26', '1', '12', '37', '32', '很好入门的！face[哈哈] ', '1', '2020-10-06 03:50:23', null, '1');
INSERT INTO `m_user_message` VALUES ('37', '1', '11', '40', '43', '好的好的face[微笑] ', '1', '2020-10-20 14:09:59', null, '0');
INSERT INTO `m_user_message` VALUES ('38', '1', '11', '40', '44', 'face[哈哈] 一定做到', '1', '2020-10-20 14:10:10', null, '0');
INSERT INTO `m_user_message` VALUES ('39', '1', '1', '34', '45', 'face[哈哈] 牛逼呀', '1', '2020-10-20 14:10:32', null, '1');
INSERT INTO `m_user_message` VALUES ('40', '1', '1', '35', '46', '可以呀。老铁face[good] ', '1', '2020-10-20 14:11:05', null, '1');
INSERT INTO `m_user_message` VALUES ('41', '1', '12', '37', '47', '简单得很face[酷] ', '1', '2020-10-20 14:11:28', null, '0');
INSERT INTO `m_user_message` VALUES ('42', '1', '12', '36', '48', 'face[哼] 装', '1', '2020-10-20 14:11:45', null, '0');
INSERT INTO `m_user_message` VALUES ('43', '1', '11', '39', '49', '哈哈', '1', '2020-10-30 07:01:15', null, '0');
INSERT INTO `m_user_message` VALUES ('44', '12', '1', '34', '50', '哈哈face[嘻嘻] ', '1', '2020-10-30 07:22:19', null, '1');
INSERT INTO `m_user_message` VALUES ('45', '12', '1', '41', '51', 'face[微笑] ', '1', '2020-10-30 07:23:30', null, '1');
