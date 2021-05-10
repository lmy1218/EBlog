<#macro layout title>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="keywords" content="fly,layui,前端社区">
        <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
        <link rel="stylesheet" href="/res/layui/css/layui.css">
        <link rel="stylesheet" href="/res/css/global.css">
        <link rel="stylesheet" href="/res/css/my.css">
        <link rel="shortcut icon" href="/res/images/mylog.png">

        <script src="/res/layui/layui.js"></script>
        <script src="/res/js/jquery.min.js"></script>
        <script src="/res/js/sockjs.js"></script>
        <script src="/res/js/stomp.js"></script>
        <script src="/res/js/im.js"></script>
        <script src="/res/js/chat.js"></script>
    </head>
    <body>

    <#--导航栏-->
    <#include "/inc/header.ftl" />
    <#--分页-->
    <#include "/inc/common.ftl" />
    <#--插槽-->
    <#nested >

    <#--尾部-->
    <#include "/inc/footer.ftl" />

    <script>
        // layui.cache.page = '';
        layui.cache.user = {
            username: '${userInfo.username ! "游客"}'
            ,uid: ${userInfo.id ! -1}
            ,avatar: '${userInfo ! "/res/images/avatar/00.jpg"}'
            ,experience: 83
            ,sex: '男'
        };
        layui.config({
            version: "3.0.0"
            ,base: '/res/mods/' //这里实际使用时，建议改成绝对路径
        }).extend({
            fly: 'index'
        }).use('fly');
    </script>
    <script>
        function showTips(count) {
            var msg = $('<a class="fly-nav-msg" href="javascript:;">'+ count +'</a>');
            var elemUser = $('.fly-nav-user');
            elemUser.append(msg);
            msg.on('click', function(){
                location.href = "/user/message";
            });
            layer.tips('你有 '+ count +' 条未读消息', msg, {
                tips: 3
                ,tipsMore: true
                ,fixed: true
            });
            msg.on('mouseenter', function(){
                layer.closeAll('tips');
            })
        }
        $(function () {
            var elemUser = $('.fly-nav-user');
            if(layui.cache.user.uid !== -1 && elemUser[0]){
                var socket = new SockJS("/websocket")
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function (frame) {
                    stompClient.subscribe("/user/" + ${userInfo.id} + "/messCount", function (res) {
                        console.log(res);
                        // 弹窗
                        showTips(res.body);
                    })
                });

            }
        });
    </script>
    <div id="page_end_html">
        <!--看板娘 - 黑猫-->
        <script src="https://eqcn.ajz.miesnfu.com/wp-content/plugins/wp-3d-pony/live2dw/lib/L2Dwidget.min.js"></script>
        <script>
            L2Dwidget.init({
                "model": {
                    jsonPath: "https://unpkg.com/live2d-widget-model-hijiki/assets/hijiki.model.json", <!--这里改模型，前面后面都要改-->
                    "scale": 1
                },
                "display": {
                    "position": "left", <!--设置看板娘的上下左右位置-->
                    "width": 100,
                    "height": 200,
                    "hOffset": 0,
                    "vOffset": 0
                },
                "mobile": {
                    "show": true,
                    "scale": 0.5
                },
                "react": {
                    "opacityDefault": 0.7, <!--设置透明度-->
                    "opacityOnHover": 0.2
                }
            });
            window.onload = function() {
                $("#live2dcanvas").attr("style", "position: fixed; opacity: 0.7; left: 70px; bottom: 0px; z-index: 1; pointer-events: none;")
            }
        </script>
    </div>
    <!-- 鼠标点击特效 -->
    <script src="https://blog-static.cnblogs.com/files/melodyjerry/cursor-effects.js"></script>
    <!-- 鼠标点击特效end -->

    <!--鼠标点击特效-->
    <script type="text/javascript">
        //定义获取词语下标
        var a_idx = 0;
        jQuery(document).ready(function ($) {
            //点击body时触发事件
            $("body").click(function (e) {
                //需要显示的词语
                var a = new Array("富强", "民主", "文明", "和谐", "自由", "平等", "公正", "法治", "爱国", "敬业", "诚信", "友善");
                //设置词语给span标签
                var $i = $("<span/>").text(a[a_idx]);
                //下标等于原来下标+1  余 词语总数
                a_idx = (a_idx + 1) % a.length;
                //获取鼠标指针的位置，分别相对于文档的左和右边缘。
                //获取x和y的指针坐标
                var x = e.pageX, y = e.pageY;
                //在鼠标的指针的位置给$i定义的span标签添加css样式
                $i.css({
                    "z-index": 999,
                    "top": y - 20,
                    "left": x,
                    "position": "absolute",
                    "font-weight": "bold",
                    "color": rand_color()
                });
                // 随机颜色
                function rand_color() {
                    return "rgb(" + ~~(255 * Math.random()) + "," + ~~(255 * Math.random()) + "," + ~~(255 * Math.random()) + ")"
                }
                //在body添加这个标签
                $("body").append($i);
                //animate() 方法执行 CSS 属性集的自定义动画。
                //该方法通过CSS样式将元素从一个状态改变为另一个状态。CSS属性值是逐渐改变的，这样就可以创建动画效果。
                //详情请看http://www.w3school.com.cn/jquery/effect_animate.asp
                $i.animate({
                    //将原来的位置向上移动180
                    "top": y - 180,
                    "opacity": 0
                    //1500动画的速度
                }, 1500, function () {
                    //时间到了自动删除
                    $i.remove();
                });
            });
        })
        ;
    </script>


    <script>

        !function(){

            function n(n,e,t){

                return n.getAttribute(e)||t

            }

            function e(n){

                return document.getElementsByTagName(n)

            }

            function t(){

                var t=e("script"),o=t.length,i=t[o-1];

                return{

                    l:o,z:n(i,"zIndex",-1),o:n(i,"opacity",.5),c:n(i,"color","0,0,0"),n:n(i,"count",99)

                }

            }

            function o(){

                a=m.width=window.innerWidth||document.documentElement.clientWidth||document.body.clientWidth,

                    c=m.height=window.innerHeight||document.documentElement.clientHeight||document.body.clientHeight

            }

            function i(){

                r.clearRect(0,0,a,c);

                var n,e,t,o,m,l;

                s.forEach(function(i,x){

                    for(i.x+=i.xa,i.y+=i.ya,i.xa*=i.x>a||i.x<0?-1:1,i.ya*=i.y>c||i.y<0?-1:1,r.fillRect(i.x-.5,i.y-.5,1,1),e=x+1;e<u.length;e++)n=u[e],

                    null!==n.x&&null!==n.y&&(o=i.x-n.x,m=i.y-n.y,

                        l=o*o+m*m,l<n.max&&(n===y&&l>=n.max/2&&(i.x-=.03*o,i.y-=.03*m),

                        t=(n.max-l)/n.max,r.beginPath(),r.lineWidth=t/2,r.strokeStyle="rgba("+d.c+","+(t+.2)+")",r.moveTo(i.x,i.y),r.lineTo(n.x,n.y),r.stroke()))

                }),

                    x(i)

            }

            var a,c,u,m=document.createElement("canvas"),

                d=t(),l="c_n"+d.l,r=m.getContext("2d"),
                x=window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||window.oRequestAnimationFrame||window.msRequestAnimationFrame||

                    function(n){

                        window.setTimeout(n,1e3/45)

                    },

                w=Math.random,y={x:null,y:null,max:2e4};m.id=l,m.style.cssText="position:fixed;top:0;left:0;z-index:"+d.z+";opacity:"+d.o,e("body")[0].appendChild(m),o(),window.onresize=o,

                window.onmousemove=function(n){

                    n=n||window.event,y.x=n.clientX,y.y=n.clientY

                },

                window.onmouseout=function(){

                    y.x=null,y.y=null

                };

            for(var s=[],f=0;d.n>f;f++){

                var h=w()*a,g=w()*c,v=2*w()-1,p=2*w()-1;s.push({x:h,y:g,xa:v,ya:p,max:6e3})

            }

            u=s.concat([y]),

                setTimeout(function(){i()},100)

        }();

    </script>
    </body>
</html>
</#macro>