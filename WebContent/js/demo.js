/**
 * Copyright 2014 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'use strict';

$(document).ready(function() {

  var MIN_WORDS = 100;
	
  var widgetId = 'vizcontainer', // Must match the ID in index.jade
    widgetWidth = 700, widgetHeight = 700, // Default width and height
    personImageUrl = 'images/app.png', // Can be blank
    language = 'en'; // language selection

  // Jquery variables
  var $content = $('.content'),
    $loading   = $('.loading'),
    $error     = $('.error'),
    $errorMsg  = $('.errorMsg'),
    $traits    = $('.traits'),
    $results   = $('.results');

/**
   * 1. Search twitter
   * 2. Call the API
   * 3. Call the methods to display the results
   */
  $('.twittersearch').click(function(){
    $('.twittersearch').blur();
    $content.val('');
    updateWordsCount();

      console.log($('.input-box').val());

    $.ajax({
      url: 'search',
      // data: {keywords: 'good'},
        data: {keywords: $('.input-box').val()},

        success: function(response) {

        if (response.error) {
          $content.val(response.error);
        } else {
          $content.val(response);
          updateWordsCount();
        }

      },
      error: function(request, textStatus, errorThrown)
      {
        $content.val("error:" + textStatus);
      }
    });
  });

  /**
   * Clear the "textArea"
   */
  $('.clear-btn').click(function(){
    $('.clear-btn').blur();
    $content.val('');
    updateWordsCount();
  });

  /**
   * Update words count on change
   */
  $content.change(updateWordsCount);

  /**
   * Update words count on copy/past
   */
  $content.bind('paste', function() {
    setTimeout(updateWordsCount, 100);
  });

  /**
   * 1. Create the request
   * 2. Call the API
   * 3. Call the methods to display the results
   */
  $('.analysis-btn').click(function(){
    $('.analysis-btn').blur();

    $loading.show();
    $error.hide();
    $traits.hide();
    $results.hide();

    $.ajax({
      type: 'POST',
      data: {
        text: $content.val(),
        language: language
      },
      url: 'main',
      dataType: 'json',
      success: function(response) {
        $loading.hide();

        if (response.error) {
          showError(response.error);
        } else {
          $results.show();
          showTraits(response);
          // showTextSummary(response);
          showVizualization(response);
        }

      },
      error: function(xhr) {
        $loading.hide();

        var error;
        try {
          error = JSON.parse(xhr.responseText || {});
        } catch(e) {}
        showError(error.error || error);
      }
    });
  });

  /**
   * Display an error or a default message
   * @param  {String} error The error
   */
  function showError(error) {
    var defaultErrorMsg = 'Error processing the request, please try again later.';
    $error.show();
    $errorMsg.text(error || defaultErrorMsg);
  }

  /**
   * Displays the traits received from the
   * Personality Insights API in a table,
   * just trait names and values.
   */
  function showTraits(data) {
    console.log('showTraits()');
    $traits.show();

    var traitList = flatten(data.tree),
      table = $traits;

    // console.log(traitList.toString());
    // console.log(JSON.stringify(traitList, null, 2));

    table.empty();

    // Header
    $('#header-template').clone().appendTo(table);

    // console.log('traitList.length: ' + traitList.length);

    // For each trait
    for (var i = 37; i < 47; i++) {
      var elem = traitList[i];

      var Klass = 'row';
      Klass += (elem.title) ? ' model_title' : ' model_trait';
      Klass += (elem.value === '') ? ' model_name' : '';

      if (elem.value !== '') { // Trait child name
        $('#trait-template').clone()
          .attr('class', Klass)
          .find('.tname')
          .find('span').html(elem.id).end()
          .end()
          .find('.tvalue')
            .find('span').html(elem.value === '' ?  '' : (elem.value + ' (± '+ elem.sampling_error+')'))
            .end()
          .end()
          .appendTo(table);
      } else {
        // Model name
        $('#model-template').clone()
          .attr('class', Klass)
          .find('.col-lg-12')
          .find('span').html(elem.id).end()
          .end()
          .appendTo(table);
      }
    }
  }

  /**
   * Construct a text representation for big5 traits crossing, facets and
   * values.
   */
  function showTextSummary(data) {
    console.log('showTextSummary()');
    var paragraphs = textSummary.assemble(data.tree);
    var div = $('.summary-div');
    $('.outputWordCountMessage').text(data.word_count_message ? '**' + data.word_count_message + '.' : ''); 
    div.empty();
    paragraphs.forEach(function(sentences) {
      $('<p></p>').text(sentences.join(' ')).appendTo(div);
    });
  }

/**
 * Renders the sunburst visualization. The parameter is the tree as returned
 * from the Personality Insights JSON API.
 * It uses the arguments widgetId, widgetWidth, widgetHeight and personImageUrl
 * declared on top of this script.
 */
function showVizualization(theProfile) {
  console.log('showVizualization()');

  console.log('theProfile: ' + theProfile);

  var traitList = flatten(theProfile.tree);
  var arr = traitList.slice(37, 47);

  console.log(arr);

  var xx = [], yy = [], tt = [];
  for (var i = 0; i < arr.length; i++) {
    xx.push(arr[i].id);
    console.log(xx);
    var v = arr[i].value;
    var vv = parseInt(v.substring(0, v.length - 1))
    yy.push(vv);
    console.log(yy);
    tt = yy;
  }

  var trace1 = {
    x: xx,
    y: yy,
    type: 'bar',
    // text: tt,
    marker: {
      color: 'rgb(158,202,225)',
      opacity: 0.6,
      line: {
        color: 'rbg(8,48,107)',
        width: 1.5
      }
    }
  };

  var data = [trace1];
  var annotationContent = [];

  var layout = {
    title: 'Report',
    annotations: annotationContent
  };

  for(var i = 0 ; i < xx.length ; i++) {
    var result = {
      x: xx[i],
      y: yy[i],
      text: tt[i],
      xanchor: 'center',
      yanchor: 'bottom',
      showarrow: false
    };
    annotationContent.push(result);
  }

  Plotly.newPlot('myDiv', data, layout);
}

  /**
   * Returns a 'flattened' version of the traits tree, to display it as a list
   * @return array of {id:string, title:boolean, value:string} objects
   */
  function flatten( /*object*/ tree) {
    var arr = [],
      f = function(t, level) {
        if (!t) return;
        if (level > 0 && (!t.children || level !== 2)) {
          arr.push({
            'id': t.name,
            'title': t.children ? true : false,
            'value': (typeof (t.percentage) !== 'undefined') ? Math.floor(t.percentage * 100) + '%' : '',
            'sampling_error': (typeof (t.sampling_error) !== 'undefined') ? Math.floor(t.sampling_error * 100) + '%' : ''
          });
        }
        if (t.children && t.id !== 'sbh') {
          for (var i = 0; i < t.children.length; i++) {
            f(t.children[i], level + 1);
          }
        }
      };
    f(tree, 0);
    return arr;
  }

  function updateWordsCount() {
    var text = $content.val();
    var wordsCount = text.match(/\S+/g) ? text.match(/\S+/g).length : 0;
    $('.inputFootnote').css('color',wordsCount < MIN_WORDS ? 'red' : 'gray');
    $('.wordsCount').text(wordsCount);
  }

  // load text data
  function onSampleTextChange() {
    var isEnglish = $('#english_radio').is(':checked');
    // language = isEnglish ? 'en' : 'es';
    language = 'en';
    
    $.get('text/' + language + '.txt').done(function(text) {
      $content.val(text);
      updateWordsCount();
    });
  }

  onSampleTextChange();
  $content.keyup(updateWordsCount);
  $('.sample-radio').change(onSampleTextChange);
});
