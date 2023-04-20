const match = document.getElementById("match");
const mismatch = document.getElementById("mismatch");
const gap = document.getElementById("gap");

function stepperMatch(btn){
    let id = btn.getAttribute("id"); 
    let min = match.getAttribute("min"); 
    let max = match.getAttribute("max"); 
    let val ; 
    if (match.getAttribute("value")=="")
         val = 0; 
    else 
         val = match.getAttribute("value"); 
    let calcStep =(id =="incrementeMatch")? 1 :  -1; 
    let newValue = parseInt(val) + calcStep ; 
    if (newValue >= min && newValue <= max)
        match.setAttribute("value", newValue); 
}

function stepperMis(btn){
    let id = btn.getAttribute("id"); 
    let min = mismatch.getAttribute("min"); 
    let max = mismatch.getAttribute("max"); 
    let val ; 
    if (mismatch.getAttribute("value")=="")
         val = 0; 
    else 
         val = mismatch.getAttribute("value"); 
    let calcStep =(id =="incrementeMis")? 1 :  -1; 
    let newValue = parseInt(val) + calcStep ; 
    if (newValue >= min && newValue <= max)
        mismatch.setAttribute("value", newValue); 
}

function stepperGap(btn){
    let id = btn.getAttribute("id"); 
    let min = gap.getAttribute("min"); 
    let max = gap.getAttribute("max"); 
    let val ; 
    if (gap.getAttribute("value")=="")
         val = 0; 
    else 
         val = gap.getAttribute("value"); 
    let calcStep =(id =="incrementeGap")? 1 :  -1; 
    let newValue = parseInt(val) + calcStep ; 
    if (newValue >= min && newValue <= max)
        gap.setAttribute("value", newValue); 
}