<#macro dateFormat date>${date?string("yyyy-MM-dd'T'HH:mmZ")}</#macro>
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