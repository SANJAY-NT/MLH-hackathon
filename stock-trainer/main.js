async function getDataClick(...args){
    if(window.isRunning === false) return;
    
    var stockData = await getData(...args);
    if(stockData){
        window.labels = stockData[0];
        window.stockPrices = stockData[1];
        document.getElementsByClassName('info-after')[0].innerHTML = 'Waiting to start';
        generateChart(document.getElementById('chart'), ['Ready', 'to go'], [0,0], ['Ready'], ['to go']);
    }
}

function buyAction(){
    if(window.isRunning === false) return;
    
    window.stockInstance.buyDecisionCount += 1;
    window.stockInstance.stocks += 1;
    window.stockInstance.wallet -= window.stockPrices[window.stockInstance.count-1];
    window.stockInstance.buyPosition.push(window.labels[window.stockInstance.count-1]);
}
function sellAction(){
    if(!window.isRunning) return;
    if(window.stockInstance.stocks <= 0){
        window.stockInstance.numberOfCheatingSells += 1;
    }else{
        window.stockInstance.sellDecisionCount += 1;
        window.stockInstance.stocks -= 1;
        window.stockInstance.wallet += window.stockPrices[window.stockInstance.count-1];
        window.stockInstance.sellPosition.push(window.labels[window.stockInstance.count-1]);
    }
}
function nextDayAction(){
    if(!window.isRunning) return;
    if(window.stockInstance.count >= window.stockPrices.length) startstopAction();
    window.stockInstance.count++;
    
    generateChart(document.getElementById('chart'),
    window.stockInstance.retainData?window.labels.slice(0, window.stockInstance.count):window.labels.slice(window.stockInstance.count-window.stockInstance.viewingLimit, window.stockInstance.count),
    window.stockInstance.retainData?window.stockPrices.slice(0, window.stockInstance.count):window.stockPrices.slice(window.stockInstance.count-window.stockInstance.viewingLimit, window.stockInstance.count),
    window.stockInstance.buyPosition, window.stockInstance.sellPosition, true);
}

function startstopAction(){
    if(window.isRunning === true){
        document.getElementById('actionButton').innerHTML = 'Start';
        document.getElementsByClassName('info-after')[0].innerHTML = 'Waiting to start';
        window.isRunning = false;
        
        generateChart(document.getElementById('chart'), window.labels, window.stockPrices, window.stockInstance.buyPosition, window.stockInstance.sellPosition);
        return;
    }
    if(!window.stockPrices){
        alert('That chart is just for show. Now get some data!');
        return;
    }
    var viewingLimit = parseInt(document.getElementById('viewing-input').value);
    if(!viewingLimit || isNaN(viewingLimit)){
        viewingLimit = 60;
    }
    if(viewingLimit >= window.stockPrices.length){
        alert('Viewing limit is greater or equal to the amount of stock prices. Adjust to a lower value');
        return;
    }
    
    document.getElementById('actionButton').innerHTML = 'Stop';
    document.getElementsByClassName('info-after')[0].innerHTML = 'Running';
    window.isRunning = true;
    
    window.stockInstance = {
        walletData: 0,
        get wallet(){
            return this.walletData;
        },
        set wallet(number){
            document.getElementsByClassName('info-after')[1].innerHTML = number.toFixed(2);
            this.walletData = number;
        },
        
        buyDecisionCountData: 0,
        get buyDecisionCount(){
            return this.buyDecisionCountData;
        },
        set buyDecisionCount(number){
            document.getElementsByClassName('info-after')[2].innerHTML = number;
            this.buyDecisionCountData = number;
        },
        
        sellDecisionCountData: 0,
        get sellDecisionCount(){
            return this.sellDecisionCountData;
        },
        set sellDecisionCount(number){
            document.getElementsByClassName('info-after')[3].innerHTML = number;
            this.sellDecisionCountData = number;
        },
        
        numberOfCheatingSellsData: 0,
        get numberOfCheatingSells(){
            return this.numberOfCheatingSellsData;
        },
        set numberOfCheatingSells(number){
            document.getElementsByClassName('info-after')[4].innerHTML = number;
            this.numberOfCheatingSellsData = number;
        },
        
        stocksData: 0,
        get stocks(){
            return this.stocksData;
        },
        set stocks(number){
            document.getElementsByClassName('info-after')[5].innerHTML = number;
            this.stocksData = number;
        },
        
        buyPosition: [],
        sellPosition: [],
        count: viewingLimit,
        retainData: document.getElementById('move-boolean').checked,
        viewingLimit
    };
    
    window.stockInstance.wallet += 0;
    window.stockInstance.buyDecisionCount += 0;
    window.stockInstance.sellDecisionCount += 0;
    window.stockInstance.numberOfCheatingSells += 0;
    window.stockInstance.stocks += 0;
    
    
    generateChart(document.getElementById('chart'),
    window.stockInstance.retainData?window.labels.slice(0, window.stockInstance.count):window.labels.slice(window.stockInstance.count-window.stockInstance.viewingLimit, window.stockInstance.count),
    window.stockInstance.retainData?window.stockPrices.slice(0, window.stockInstance.count):window.stockPrices.slice(window.stockInstance.count-window.stockInstance.viewingLimit, window.stockInstance.count),
    window.stockInstance.buyPosition, window.stockInstance.sellPosition);
}

document.addEventListener('keydown', function(event){
    if(!event.ctrlKey && !event.altKey && !event.shiftKey){
        if(event.key === '1'){
            buyAction();
        }else if(event.key === '2'){
            sellAction();
        }else if(event.key === '3'){
            nextDayAction();
        }
    }
})