var viewArray = new Array();
var ve = document.getElementsByClassName('views-elem');

function addNewView(elem){
	//chceck if we add a 'unique' view
	if(elem.viewName==="navbar"||elem.viewName==="simple-footer"){
		//check if this 'unique' view is already in our array
		for(i = 0 ; i<viewArray.length;i++){
			//if there is duplicate we won't proccess any further
			if(viewArray[i].viewName===elem.viewName)
				return;
		}		
	}
	
	//there is no duplicate we can add element to our array
	//lets check if we add navigation element in this case we need to add it at the very beggining
	if(elem.viewName==="navbar"){
		viewArray.unshift(elem);
		displayElem(elem);
	}
	else if(elem.viewName==="simple-footer"){
		viewArray.push(elem);
		displayElem(elem);
	}
	else{
		//its neither navbar nor footer, so we need to add it between them
		//lets firstly check if footer is at the end
		if(viewArray.length>0&&viewArray[viewArray.length-1].viewName==="simple-footer"){
			//we need to pop footer, push our element, and push our footer back
			var temp = viewArray.pop();
			viewArray.push(elem);
			viewArray.push(temp);
			displayElem(elem);
		}else{
			//there is no footer we can simply push our elem even if array is empty
			viewArray.push(elem);
			displayElem(elem);
		}
	}
	console.log("current view arr: " + viewArray);
}

//helper function which is used when we add completly new view
function addNewViewFromTemplate(name){
	var j = -1;
	for(i = 0; i<viewTemplates.length; i++){
		if(viewTemplates[i].viewName===name){
			addNewView(viewTemplates[i]);
			break;
		}
	}	
}

//add current views
for(i=0; i<pageViews.views.length; i++){
	addNewView(pageViews.views[i]);
}

/*you may ask why 'move up' button use function called 'moveDown',
 *thats because when the element 'go up' on the display its order is lower
 *(for example navigation has the lowest order its at the index 0, even tough its the first element to be displayed)
 */
function getElementAsTableRow(elem){
	return "<tr class='views-elem'>" +
			"<th scope='row' class='view-elem-name'>" + elem.viewName + "</th>" +
			"<th><button onclick='moveDownElem(this)' class='btn btn-light'><i class='fas fa-arrow-up'></i></button></th>" +
			"<th><button onclick='moveUpElem(this)' class='btn btn-light'><i class='fas fa-arrow-down'></i></button></th>" +
			"<th><button onclick='changeElementSetting(this)' class='btn btn-light'><i class='fas fa-cog'></i></button></th>" +
			"<th><button onclick='deleteElem(this)' class='btn btn-light'><i class='fas fa-trash'></i></button></th>" +
			"</tr>";
}

//display element in certain div contianer
function displayElem(elem){
	var cv = document.getElementById('current-views');
	
	if(elem.viewName==="navbar"){
		//we need to add it at the beggining
		if(ve.length===0){
			//no element yet so we can add it to the container
			cv.innerHTML = getElementAsTableRow(elem);
		}else{
			//there is already some element so we need to add it before everything else
			ve[0].insertAdjacentHTML('beforebegin', getElementAsTableRow(elem));
		}
	}
	else if(elem.viewName==="simple-footer"){
		//we need to add it at the end
		if(ve.length===0){
			//no element yet so we can add it to the container
			cv.innerHTML = getElementAsTableRow(elem);
		}else{
			//there is already some element so we need to add it after everything else
			ve[ve.length-1].insertAdjacentHTML('afterend', getElementAsTableRow(elem));
		}
	}
	else{
		//thats not 'unique' element so we just add it at the end
		if(ve.length===0){
			//no element yet so we can add it to the container
			cv.innerHTML = getElementAsTableRow(elem);
		}else{
			//there is already some element so we need to add it after everything else but not after footer
			if(viewArray[viewArray.length-1].viewName==="simple-footer"){
				ve[ve.length-1].insertAdjacentHTML('beforebegin', getElementAsTableRow(elem));
			}
			else{
				ve[ve.length-1].insertAdjacentHTML('afterend', getElementAsTableRow(elem));
			}
		}
	}
	
}

function findElemInViewArray(elem){
	var selectedElem = elem.parentElement.parentElement;
	var veOrderNumber = -1;
	for(i = 0; i<ve.length; i++){
		if(selectedElem===ve[i]){
			veOrderNumber = i;
			break;			
		}
	}
	return veOrderNumber;
}

function moveUpElem(elem){
	elemIndex = findElemInViewArray(elem);
	//if different then -1, less then array length-1 and it is not navigation element we can move up
	if(elemIndex!=-1&&elemIndex<viewArray.length-1&&viewArray[elemIndex].viewType!=='nav'){
		//if we move last but one element we need to check if the last element isn't 'footer'
		if(elemIndex===viewArray.length-2&&viewArray[viewArray.length-1].viewType==='footer'){
			return;
		}
		//now we are sure everything is ok, so we move item up
		viewArray = changeItemsAtIndexes(viewArray, elemIndex);
	}
}

function moveDownElem(elem){
	elemIndex = findElemInViewArray(elem);
	//if more than -1, less than array length-1 and it is not navigation element we can move down
	if(elemIndex>0&&elemIndex<viewArray.length&&viewArray[elemIndex].viewType!=='footer'){
		//if we move second element we need to check if the first element isn't navigation
		if(elemIndex===1&&viewArray[0].viewType==='nav'){
			return;
		}
		//now we are sure everything is ok, so we move item up
		viewArray = changeItemsAtIndexes(viewArray, elemIndex-1);
	}
}

function deleteElem(elem){
	elemIndex = findElemInViewArray(elem);
	//if we found that element we can safely delete
	if(elemIndex>-1){
		viewArray = removeElemAtIndex(viewArray,elemIndex);
		ve[elemIndex].remove();		
	}	
}

function removeElemAtIndex(arr,index){
    return arr.slice(0,index).concat(arr.slice(index + 1));
}

//change element at [index] with element at [index+1]
function changeItemsAtIndexes(arr,index){
	var listOfNameElem = document.getElementsByClassName('view-elem-name');
	var temp = listOfNameElem[index].textContent;
	listOfNameElem[index].textContent = listOfNameElem[index+1].textContent;
	listOfNameElem[index+1].textContent = temp;
	return arr.slice(0,index).concat(arr.slice(index+1,index+2)).concat(arr.slice(index,index+1)).concat(arr.slice(index+2));
}

function changeElementSetting(elem){
	elemIndex = findElemInViewArray(elem);
	if(elemIndex>-1){
		//set title of modal
		document.getElementById('myModalTitle').innerHTML = viewArray[elemIndex].viewName;
		
		//set body of modal (labels+inputs)
		var temp = "<input id='myModalCurrentElemIndex' type='hidden' value='" + elemIndex + "' />";
		viewArray[elemIndex].props.forEach(function(item){
			temp += "<div><label>" + item.name + ":</label></div>" +
					"<div><input id='" + item.name + "' value='" + item.value + "' /></div>"
		})
		document.getElementById('myModalBody').innerHTML = temp;
		
		//show modal
		$('#myModal').modal('show');
	}
}

function saveChangesInViewArray(){
	elemIndex = document.getElementById('myModalCurrentElemIndex').value;
	for(i = 0; i<viewArray[elemIndex].props.length; i++){
		var prop = viewArray[elemIndex].props[i];
		if(document.getElementById(prop.name).value!==prop.value){
			//update value if its different
			prop.value = document.getElementById(prop.name).value
		}
	}

	$('#myModal').modal('hide');
}

function saveNewPageVersion(){
	$.ajax({    
		headers: {          
		    Accept: "application/json",         
		    "Content-Type": "application/json"   
		}, 
	    type : "POST",
	    url : "/admin/editor",
	    data : JSON.stringify({'views':viewArray}),
	    dataType: 'json',
	    success : function(response) {
	       alert('Success ! Go to Index to see your changes.');
	    },
	    error : function(e) {
	       alert('Error !');
	       console.log(e);
	    }
	});
}