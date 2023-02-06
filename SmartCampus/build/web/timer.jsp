<!DOCTYPE html>
<html>
<body>

<h1>The Window Object</h1>
<h2>The setInterval() and clearInterval() Methods</h2>

<p id="demo"></p>

<button onclick="myStop()">Stop the time</button>

<script>
const myInterval = setInterval(myTimer, 1000);
var seconds=300;
var fiveMin=0;
function myTimer() {
    var timeleft =seconds -fiveMin; // let's say now is 01:30, then current seconds is 60+30 = 90. And 90%300 = 90, finally 300-90 = 210. That's the time left!
 
    fiveMin=fiveMin+1;
    var result = parseInt(timeleft / 60) + ':' + timeleft % 60; //formart seconds back into mm:ss 
    document.getElementById('demo').innerHTML = result;
    if(timeleft<=0)
    {
         clearInterval(myInterval);
    }
}

function myStop() {
  clearInterval(myInterval);
}
</script>

</body>
</html>