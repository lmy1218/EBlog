<#--导入公共部分-->
<#include "/inc/layout.ftl" />

<#--使用-->
<@layout "错误信息" >
  <div class="layui-container fly-marginTop">
    <div class="fly-panel">
      <div class="fly-none">
        <h2><i class="iconfont icon-tishilian"></i></h2>
        <p>${msg}</p>
      </div>
    </div>
  </div>
</@layout>