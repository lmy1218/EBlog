<#--右边-->
<div class="layui-col-md4">

    <#--本周热议-->
    <dl class="fly-panel fly-list-one">
        <dt class="fly-panel-title">本周热议</dt>
        <@hots>
            <#list results as post>
                <dd>
                    <a href="/post/${post.id}">${post.title}</a>
                    <span><i class="iconfont icon-pinglun1"></i> ${post.commentCount}</span>
                </dd>
            </#list>
        </@hots>


        <!-- 无数据时 -->
        <!--
        <div class="fly-none">没有相关数据</div>
        -->
    </dl>

    <div class="fly-panel fly-signin">
        <div class="fly-panel-title">
            签到
            <i class="fly-mid"></i>
            <a href="javascript:;" class="fly-link" id="LAY_signinHelp">说明</a>
            <i class="fly-mid"></i>
            <a href="javascript:;" class="fly-link" id="LAY_signinTop">活跃榜<span class="layui-badge-dot"></span></a>
            <span class="fly-signin-days">已连续签到<cite>16</cite>天</span>
        </div>
        <div class="fly-panel-main fly-signin-main">
            <button class="layui-btn layui-btn-danger" id="LAY_signin">今日签到</button>
            <span>可获得<cite>5</cite>飞吻</span>

            <!-- 已签到状态 -->
            <!--
            <button class="layui-btn layui-btn-disabled">今日已签到</button>
            <span>获得了<cite>20</cite>飞吻</span>
            -->
        </div>
    </div>

    <div class="fly-panel fly-rank fly-rank-reply" id="LAY_replyRank">
        <h3 class="fly-panel-title">回贴周榜</h3>
        <@reply>
            <#list results as user>
                <dl>
                    <!--<i class="layui-icon fly-loading">&#xe63d;</i>-->
                    <dd>
                        <a href="user/${user.userId}">
                            <img src="${user.avatar}"><cite>${user.username}</cite><i>${user.count}次回答</i>
                        </a>
                    </dd>
                </dl>
            </#list>
        </@reply>
<#--        <dl>-->
<#--            <!--<i class="layui-icon fly-loading">&#xe63d;</i>&ndash;&gt;-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--            <dd>-->
<#--                <a href="user/home.html">-->
<#--                    <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"><cite>贤心</cite><i>106次回答</i>-->
<#--                </a>-->
<#--            </dd>-->
<#--        </dl>-->
    </div>
    <div class="fly-panel">
        <h3 class="fly-panel-title">温馨通道</h3>
        <ul class="fly-panel-main fly-list-static">
            <li>
                <a href="http://fly.layui.com/jie/4281/" target="_blank">layui 的 GitHub 及 Gitee (码云) 仓库，欢迎Star</a>
            </li>
            <li>
                <a href="http://fly.layui.com/jie/5366/" target="_blank">
                    layui 常见问题的处理和实用干货集锦
                </a>
            </li>
            <li>
                <a href="http://fly.layui.com/jie/4281/" target="_blank">layui 的 GitHub 及 Gitee (码云) 仓库，欢迎Star</a>
            </li>
            <li>
                <a href="http://fly.layui.com/jie/5366/" target="_blank">
                    layui 常见问题的处理和实用干货集锦
                </a>
            </li>
            <li>
                <a href="http://fly.layui.com/jie/4281/" target="_blank">layui 的 GitHub 及 Gitee (码云) 仓库，欢迎Star</a>
            </li>
        </ul>
    </div>

<#--    <div class="fly-panel">-->
<#--        <div class="fly-panel-title">-->
<#--            这里可作为广告区域-->
<#--        </div>-->
<#--        <div class="fly-panel-main">-->
<#--            <a href="http://layim.layui.com/?from=fly" target="_blank" class="fly-zanzhu" time-limit="2017.09.25-2099.01.01" style="background-color: #5FB878;">LayIM 3.0 - layui 旗舰之作</a>-->
<#--        </div>-->
<#--    </div>-->

    <div class="fly-panel fly-link">
        <h3 class="fly-panel-title">友情链接</h3>
        <dl class="fly-panel-main">
            <dd><a href="http://www.layui.com/" target="_blank">layui</a><dd>
            <dd><a href="http://layim.layui.com/" target="_blank">WebIM</a><dd>
            <dd><a href="http://layer.layui.com/" target="_blank">layer</a><dd>
            <dd><a href="http://www.layui.com/laydate/" target="_blank">layDate</a><dd>
            <dd><a href="mailto:xianxin@layui-inc.com?subject=%E7%94%B3%E8%AF%B7Fly%E7%A4%BE%E5%8C%BA%E5%8F%8B%E9%93%BE" class="fly-link">申请友链</a><dd>
        </dl>
    </div>

</div>