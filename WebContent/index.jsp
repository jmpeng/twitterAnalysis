<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="com.ibm.cloudoe.samples.i18n.messages" />
<!DOCTYPE html>
<html lang="${language}">

<head>
<title>University of Windsor Group 10</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/watson-bootstrap-dark.css">
<link rel="stylesheet" href="css/banner.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>This is the header!</h1>
	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-md-12 col-xs-12">
				<h2>Text Input</h2>
				<p>Please input text</p>
				<div class="well">
					<div class="form-group row">
						<div style="padding: 0px;" class="col-lg-12 col-xs-12">
							<%--<label class="col-lg-6 col-md-6 col-xs-6 control-label">Choose Language:</label>--%>
							<%--<div class="col-lg-6 col-md-6 col-xs-6 sample-radio-list">--%>
								<%--<div class="sample-radio-container">--%>
									<%--<label><input id="english_radio" type="radio"--%>
										<%--name="sample_text" value="en" checked class="sample-radio">English</label>--%>
								<%--</div>--%>
								<%--<div class="sample-radio-container">--%>
									<%--<label><input type="radio" name="sample_text"--%>
										<%--value="es" class="sample-radio">spanish</label>--%>
								<%--</div>--%>
							<%--</div>--%>
							<textarea rows="12" required="true" name="text"
								placeholder="content"
								class="content form-control"></textarea>
							<div class="text-right inputFootnote">
								<span class="wordsCount"></span> <span class="small"></span>words</span>
							</div>
						</div>
					</div>
					<div style="display: none; margin-bottom: 10px;"
						class="form-group row captcha">
						<div data-sitekey="6LcRbQkTAAAAAGUFVbnuqDfse-XZASLZwoC34oJV"
							class="col-lg-12 col-md-12 col-xs-12 g-recaptcha"></div>
					</div>
					<div class="form-group row buttons-container">
						<div class="col-lg-4 col-xs-4">
							<button type="button" class="btn btn-block clear-btn">clear</button>
						</div>
						<div class="col-lg-4 col-lg-push-4 col-xs-4 col-xs-push-4">
							<button type="button" class="btn btn-block analysis-btn">analyze</button>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-6 col-md-12 col-xs-12">
				<div style="display: none;"
					class="form-group row loading text-center loading">
					<h2>&nbsp;</h2>
					<img src="images/watson.gif">
				</div>
				<div style="display: none;" class="form-group row error">
					<h2>&nbsp;</h2>
					<div class="well">
						<p class="errorMsg"></p>
					</div>
				</div>
				<div style="display: none;" class="results">
					<h2>Personality*</h2>
					<div class="well">
						<div class="summary-div"></div>
						<div style="color: gray" class="text-right">
							<em class="small">*Compared to most people who participated in our surveys</em>
						</div>
						<div style="color: gray" class="text-right">
							<em class="small outputWordCountMessage"></em>
						</div>
					</div>
				</div>
			</div>
			<div style="display: none;" class="results">
				<div class="row">
					<div>
						<h3>Data Behind Your Personality</h3>
						<div style="display: none;" class="col555px well traits"></div>
					</div>
				</div>
			</div>
			<div class="hidden">
				<div id="header-template">
					<div class="row theader">
						<div class="col-lg-5 col-xs-5">
							<span>Name</span>
						</div>
						<div class="col-lg-7 col-xs-7 text-right">
							<span>value ± Sampling Error</span>
						</div>
					</div>
				</div>
				<div id="trait-template">
					<div class="row">
						<div class="tname col-lg-7 col-xs-7">
							<span></span>
						</div>
						<div class="tvalue col-lg-5 col-xs-5 text-right">
							<span></span>
						</div>
					</div>
				</div>
				<div id="model-template">
					<div class="row">
						<div class="col-lg-12 col-xs-12 text-center">
							<span></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		Visualization of Personality Data
		<div id="myDiv" style="width: 700px; height: 700px;"></div>
	</div>
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="js/d3.v2.min.js"></script>
	<script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
	<script type="text/javascript" src="js/demo.js"></script>
	<script type="text/javascript" src="js/personality.js"></script>
    <script type="text/javascript" src="js/string-utils.js"></script>
    <script type="text/javascript" src="js/i18n.js"></script>
	<script type="text/javascript" src="js/textsummary.js"></script>
    <script type="text/javascript">
        textSummary.init('json');
    </script>
</body>
</html>