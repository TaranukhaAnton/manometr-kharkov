<%@ page import="org.krams.tutorial.view.BaseTreeNode" %>
<%@ taglib uri="/WEB-INF/java2s.tld" prefix="java2s" %>

<div class="menu" id="menu" >
    <div>
        <a href="javascript:void($('#groupSelectDialog').dialog('open'))">Select Group</a>
        <a href="javascript:void(expand())">Expand 33</a>
    </div>

    <div id="leftMenuSP">
        <div id="leftMenu"/>
    </div>

    <div id="groupSelectDialog" title="Select Group">
        <% BaseTreeNode baseTreeNode = (BaseTreeNode) request.getAttribute("baseTreeNode");%>
        <java2s:Hello rootTreeNode="${baseTreeNode}"></java2s:Hello>
    </div>

</div>