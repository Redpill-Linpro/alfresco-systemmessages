<#macro dateFormat date=""><#if date?is_date>${xmldate(date)}</#if></#macro>
<#escape x as jsonUtils.encodeJSONString(x)>
{
	"notifications":
	[
		<#list notifications as item>
		{
			"nodeRef": "${item.nodeRef}",
			"id": "${item.id}",
			"title": "${item.title!''}",
			"text": "${item.message!''}",
			"type": "${item.priority!''}",
			"startTime": "<@dateFormat item.startTime />",
			"endTime": "<@dateFormat item.endTime />"
		}<#if item_has_next>,</#if>
		</#list>
	]
}
</#escape>