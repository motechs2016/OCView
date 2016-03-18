// JavaScript Document
var ry={};
ry.tools={};
//通过class获取元素
function getByClass(oParent,sClass){
	   var aEle=oParent.getElementsByTagName('*');
	   var aResult=[];
	   for(var i=0;i<aEle.length;i++){
		     if(aEle[i].className==sClass){
				   aResult.push(aEle[i]);
				 }
		   }
	   return aResult;
	}

function getStyle(obj,name){//用到获取样式
   if(obj.currentStyle){
	     return obj.currentStyle[name];
	   }else{
		   return getComputedStyle(obj,false)[name];
		   }
	
}
//运动框架
//json的用法 var json={width:400,height:300};多次循环
function startMove(obj,json,fnEd){
  var bStop=true;//假设所有的目标值都已经实现
  clearInterval(obj.timer);
  obj.timer=setInterval(function(){
	  for(var attr in json){
		   var cur=0;
	   if(attr=='opacity'){
		     cur=Math.round(parseFloat(getStyle(obj,attr))*100);
		   }else{
	         cur=parseInt(getStyle(obj,attr));
		   }
	    var speed=(json[attr]-cur)/10;
		speed=speed>0?Math.ceil(speed):Math.floor(speed);
		if(cur!=json[attr]){
			 bStop=false;
			}
		if(cur==json[attr]){
			if(bStop){
			clearInterval(obj.timer);
			if(fnEd){
				  fnEd();
			}
				}
			}else{
				  if(attr=='opacity'){
					    obj.style.filter='alpha(opacity:'+(cur+speed)+')';
						obj.style.opacity=(cur+speed)/100;
					  }else{				 
				      obj.style[attr]=cur+speed+'px';
					  }
				}
		  }
	  
	  },30);
}

ry.vi={};
    ry.vi.textChange=function(obj,str){
		 obj.onfocus=function(){
			  if(this.value==str){
				   this.value='';
				  }
			 }
	     obj.onblur=function(){
			  if(this.value==''){
				   this.value=str;
				  }
			 }
		
		}
 ry.vi.fadeOut=function(obj){
	 var value=0;
	 clearInterval(obj.timer)
	 obj.timer=setInterval(function(){
		 var speed=10;
		  if(value==100){
			    clearInterval(obj.timer);
			  }else{
				   value+=speed;
				   obj.style.opacity=value/100;
				   obj.style.filter='alpha(opacity='+value+')';
				  }
		 
		 },30)
	 
	 }
   ry.vi.fadeIn=function(obj){
	 var value=100;
	 clearInterval(obj.timer)
	 obj.timer=setInterval(function(){
		 var speed=-10;
		  if(value==0){
			    clearInterval(obj.timer);
			  }else{
				   value+=speed;
				   obj.style.opacity=value/100;
				   obj.style.filter='alpha(opacity='+value+')';
				  }
		 
		 },30)
	 
	 }
  ry.vi.opacity=function(obj){
	     for(var i=0;i<obj.length;i++){
				  obj[i].onmouseover=function(){
					    startMove(this,{opacity:50});
						
					  }
				  obj[i].onmouseout=function(){
					    startMove(this,{opacity:100});
						
					  }
				 }
	  
	  }
  ry.vi.displayValue=function(objParent,obj){
	    objParent.onfocus=function(){
			  obj.style.display='none';
			}
		objParent.onblur=function(){
			 if(this.value==''){
			  obj.style.display='block';
			  }
			}
	  
	  }
ry.app={};
    
	ry.app.toText=function(){
		  var oText=document.getElementById('text');
		  ry.vi.textChange(oText,"搜索视频，公开课");
		}
	ry.app.toName=function(){
		  var oName=document.getElementById('name');
		  var oNameValue=document.getElementById('name_value');
		     ry.vi.displayValue(oName,oNameValue);
		}
	
	ry.app.passsword=function(){
		 var oPsw=document.getElementById('psw');
		 var oPswValue=document.getElementById('psw_value');		
		  ry.vi.displayValue(oPsw,oPswValue);		
		}
	ry.app.trueLogin=function(){
		 var oTurepsw=document.getElementById('turepsw');
		 var oEmail=document.getElementById('email');
		 var oEmailValue=document.getElementById('email_value');
		 var oPswTrue=document.getElementById('psw_true');
		 var oSignIn=document.getElementById("signIn");
		 var oNickName=document.getElementById('nickName');
		 var oNickNameValue=document.getElementById('nickName_value');
		 var oZhuce=document.getElementById('zhuce');
		 var oDenglu=document.getElementById('denglu');
		 var oSignInwrapper=document.getElementById('signInwrapper');
		  oSignIn.onclick=function(){
			  oTurepsw.style.display="block";
			  if(oTurepsw.value==''){oPswTrue.style.display='block';} 
			  oNickName.style.display="block";
			  if(oNickName.value==""){oNickNameValue.style.display="block";}
			  oEmail.style.display="block";
			  if(oEmail.value==""){oEmailValue.style.display="block";}
			  oDenglu.style.display='none';
			  oZhuce.style.display='block';
			  oSignInwrapper.style.display='none';
			 
			 }
		   ry.vi.displayValue(oTurepsw,oPswTrue);
		   ry.vi.displayValue(oNickName,oNickNameValue);
		   ry.vi.displayValue(oEmail,oEmailValue);
		}
	
	ry.app.toPlay=function(){
		  var oImg=document.getElementById('img');
		  var aLi=oImg.getElementsByTagName('li');
		  //alert(aLi.length);
		  var inow=0;
		  var timer=setInterval(auto,3000);
		  function auto(){
			 
			   if(inow==aLi.length-1){
				     inow=0;
				   }else{
					    inow++;
					   }
		         for(var i=0;i<aLi.length;i++){
				      ry.vi.fadeIn(aLi[i]);
				   }
				      ry.vi.fadeOut(aLi[inow]);
			 // alert(inow);
			  }
		}
	 ry.app.toDisplay=function(){
		 var oNav=document.getElementById("nav");
		 var aDiv=oNav.getElementsByTagName('div');
		 var aLi=oNav.getElementsByTagName('li');
		 //alert(aDiv.length);
		 //alert(aLi.length);
		 for(var i=0;i<aLi.length;i++){
			  aLi[i].index=i;
			  aLi[0].className="active"
			  aLi[i].onmouseover=function(){	 
				  for(var i=0;i<aLi.length;i++){
						 aDiv[i].style.display="none";
						 aLi[i].className='';
					  }
					     aDiv[this.index].style.display="block";
						 this.className="active";
				 }
			  
			 }
			
	   }
	   ry.app.toNews=function(){
		   var oHot=document.getElementById('hot');
		   var oHotnews=document.getElementById('hotnews');
		   var aspan=oHotnews.getElementsByTagName('span');
		   var   aUl=oHot.getElementsByTagName('ul');
		    for(var i=0;i<aspan.length;i++){
			    aspan[i].index=i;
				//aUl[0].style.display="block";
			     aspan[i].onmouseover=function(){
					  for(var i=0;i<aspan.length;i++){
						  aspan[i].className='';
						   aUl[i].style.display='none';
						  }
						 aUl[this.index].style.display="block";	
						  aspan[this.index].className="span_active"
											 
					}	
			   }
		  
		   
		   }
		 ry.app.opacity=function(){
			
			 var oCol1=document.getElementById('col1');
			 var oCol2=document.getElementById('col2');
			 var oCol3=document.getElementById('col3');
			 var oCol4=document.getElementById('col4');
			 var oCol5=document.getElementById('col5');
			 var oCol6=document.getElementById('col6');
			 var aImg1=oCol1.getElementsByTagName('img');
			 var aImg2=oCol2.getElementsByTagName('img');
			 var aImg3=oCol3.getElementsByTagName('img'); 
			 var aImg4=oCol4.getElementsByTagName('img');
			 var aImg5=oCol5.getElementsByTagName('img');
			 var aImg6=oCol6.getElementsByTagName('img');
			    ry.vi.opacity(aImg1);
			    ry.vi.opacity(aImg2);
				ry.vi.opacity(aImg3);
				ry.vi.opacity(aImg4);
				ry.vi.opacity(aImg5);
				ry.vi.opacity(aImg6);
			 
			}
		ry.app.closeLogin=function(){
			 var oLogin=document.getElementById('login');
			 var oClose=document.getElementById('close');
			var oWrapper=document.getElementById('wrapper');			 
			 oClose.onclick=function(){
				 $("#video").show();
				   oLogin.style.display="none";
				   oWrapper.className="";
				   oWrapper.style.height=0; 
				 }
			
			
			}
		ry.app.openLogin=function(){
			var oLoginPage=document.getElementById('loginPage');
		    var oLogin=document.getElementById('login');
			var oWrapper=document.getElementById('wrapper');
			var oContent=document.getElementById('wrapperContent');
		    var oTurepsw=document.getElementById('turepsw');
		    var oEmail=document.getElementById('email');
			var oEmailValue=document.getElementById('email_value');
		    var oPswTrue=document.getElementById('psw_true');
			var oHeight=oContent.offsetHeight;
			var oNick=document.getElementById('nickName');
			var oNickValue=document.getElementById('nickName_value');
			var oZhuce=document.getElementById('zhuce');
			var oDenglu=document.getElementById('denglu');
			var oSignInwrapper=document.getElementById('signInwrapper');
			 if(oLoginPage){
			oLoginPage.onclick=function(){
				   oLogin.style.display="block";
				    oTurepsw.style.display="none";
			        oPswTrue.style.display='none';
					oNick.style.display='none';
					oNickValue.style.display='none';
			        oEmail.style.display="none";
					oEmailValue.style.display='none';
					oZhuce.style.display='none';
					oDenglu.style.display="block";
					oSignInwrapper.style.display='block';
				   oWrapper.className="wrapper";
				   oWrapper.style.height=oHeight+'px';
				 }
			 }
			}
			ry.app.button=function(){
				var oLoginPage=document.getElementById('loginPage');
				var oLogin=document.getElementById('login');
				var oWrapper=document.getElementById('wrapper');
				var oContent=document.getElementById('wrapperContent');
				var oTurepsw=document.getElementById('turepsw');
				var oEmail=document.getElementById('email');
				var oEmailValue=document.getElementById('email_value');
				var oPswTrue=document.getElementById('psw_true');
				var oHeight=oContent.offsetHeight;
				var oNick=document.getElementById('nickName');
				var oNickValue=document.getElementById('nickName_value');
				var oZhuce=document.getElementById('zhuce');
				var oDenglu=document.getElementById('denglu');
				var oSignInwrapper=document.getElementById('signInwrapper');
				var Opinion=document.getElementById('opinion');
				var aA=Opinion.getElementsByTagName('a');
				var oVote=document.getElementById("vote");
				var oFabiao=document.getElementById('fabiao');
				if(oLoginPage!=null){
				oLoginPage.onclick=function(){
				   oLogin.style.display="block";
				    oTurepsw.style.display="none";
			        oPswTrue.style.display='none';
					oNick.style.display='none';
					oNickValue.style.display='none';
			        oEmail.style.display="none";
					oEmailValue.style.display='none';
					oZhuce.style.display='none';
					oDenglu.style.display="block";
					oSignInwrapper.style.display='block';
				   oWrapper.className="wrapper";
				   oWrapper.style.height=oHeight+'px';
				 }
				}
				 if(loginFlag){
					 oFabiao.onclick=oLoginPage.onclick;
					 oFabiao.href="#wrapperContent";
					 if(oVote!=null){
						 oVote.onclick=oFabiao.onclick;
						 oVote.href="#wrapperContent";
					 }
					 
					}
			 for(var i=0;i<aA.length;i++){
				   if(loginFlag){
					    aA[i].onclick=oLoginPage.onclick;
						aA[i].href='#wrapperContent'
					   }
				 }
			}
			ry.app.button2=function(){
				var oLoginPage=document.getElementById('loginPage');
				var oLogin=document.getElementById('login');
				var oWrapper=document.getElementById('wrapper');
				var oContent=document.getElementById('wrapperContent');
				var oTurepsw=document.getElementById('turepsw');
				var oEmail=document.getElementById('email');
				var oEmailValue=document.getElementById('email_value');
				var oPswTrue=document.getElementById('psw_true');
				var oHeight=oContent.offsetHeight;
				var oNick=document.getElementById('nickName');
				var oNickValue=document.getElementById('nickName_value');
				var oZhuce=document.getElementById('zhuce');
				var oDenglu=document.getElementById('denglu');
				var oSignInwrapper=document.getElementById('signInwrapper');
				var Opinion=document.getElementById('opinion');
				var aA=Opinion.getElementsByTagName('a');
				var oFabiao=document.getElementById('fabiao');
			if(oLoginPage!=null){
				oLoginPage.onclick=function(){
					//$("#video").hide();
				   oLogin.style.display="block";
				    oTurepsw.style.display="none";
			        oPswTrue.style.display='none';
					oNick.style.display='none';
					oNickValue.style.display='none';
			        oEmail.style.display="none";
					oEmailValue.style.display='none';
					oZhuce.style.display='none';
					oDenglu.style.display="block";
					oSignInwrapper.style.display='block';
				   oWrapper.className="wrapper";
				   oWrapper.style.height=oHeight+'px';
				 }
			}
				
				 if(loginFlag){
					oFabiao.onclick=oLoginPage.onclick;
					oFabiao.href="#wrapperContent";
					 
					 }
				 for(var i=0;i<aA.length;i++){
				   if(loginFlag){
					    aA[i].onclick=oLoginPage.onclick;
						aA[i].href='#wrapperContent'
					   }
				 }
			}
		
		
		ry.app.opinion=function(){
			 var Opinion=document.getElementById('opinion');
			 var aPinion=Opinion.getElementsByTagName('label');
			 var aReplay=Opinion.getElementsByTagName('div');
			 var aButton=Opinion.getElementsByTagName('button');
			 var aTextarea=Opinion.getElementsByTagName('textarea');
			 //alert(aReplay.length);
			 	for(var i=0;i<aPinion.length;i++){
					  aPinion[i].index=i;
					  aButton[i].index=i;
					  aPinion[i].onclick=function(){
						   for(var i=0;i<aPinion.length;i++){
							    aReplay[i].style.display="none";
							   }
							    aReplay[this.index].style.display="block";
						  
						  }
					  aButton[i].onclick=function(){						
							   aReplay[this.index].style.display="none";
						  }					
					}
					 
			}
		
		ry.app.textareaHover=function(){
			  var oViews=document.getElementById('col2Views')
			  var oViewsValue=document.getElementById('col2ViewsValue');
			  var oPhone=document.getElementById('phone');
			  var oPhoneValue=document.getElementById('textValue')
			  oViews.onclick=function(){
				   this.className='textareaHover';
				  }
			  oViews.onfocus=function(){
				    oViewsValue.style.display='none';
				   }
			  oViews.onblur=function(){
				    if(oViews.value==''){
						 oViewsValue.style.display='block';
						}
				  }
			  oPhone.onclick=function(){
				  this.className='textareaHover';
				 }
			  oPhone.onfocus=function(){
				   oPhoneValue.style.display='none';
				  }
			  oPhone.onblur=function(){
				    if(oPhone.value==''){
						 oPhoneValue.style.display='block';
						}
				  }
			}
		