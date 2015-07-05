<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Question Answer Java Starter Application</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/watson-bootstrap-dark.css">
<link rel="stylesheet" href="css/browser-compatibility.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="container">
		<div class="header row">
			<div class="col-lg-3">
				<img src="images/app.png">
			</div>
			<div class="col-lg-8">
				<h2>Question Answer Java Starter Application</h2>
				<p>The IBM Watson Question Answer (QA) service provides an API,
					referred to as the QAAPI, that enables you to add the power of the
					IBM Watson cognitive computing system to your application. With
					this service, you can connect to Watson, post questions, and
					receive responses that you can use in your application.</p>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<h2>Try the service</h2>
				<div class="well">
					<form id='qaForm' method="post" class="form-horizontal" action="demo">
						<fieldset>
							<div class="form-group row">
								<h4 class="col-lg-12">Select corpus:</h4>
								<div class="col-lg-12">
									<select id="select" name="dataset" class="form-control">
										<option value="healthcare"
										<%=(request.getParameter("dataset") != null && request.getParameter("dataset").equals("travel")) ? "" : "selected" %>
										>Healthcare</option>
										<option value="travel"
										<%=(request.getParameter("dataset") != null && request.getParameter("dataset").equals("travel")) ? "selected" : "" %>
										>Travel</option>
									</select>
								</div>
							</div>
							<div class="form-group row">
								<div class="col-lg-10">
									<input id="questionText" name="questionText" placeholder="Type a question..."
										required class="form-control" autofocus value="${questionText}">
										<a class="btn btn-link"
										href="javascript:showExample(true)"
										style="padding-left:0px;" >Try an example question</a>
										</div>
								<div class="col-lg-2">
									<button type="submit" class="btn btn-block">Ask</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<c:if test="${not empty questionText }">
					<h2>Answers and Confidence</h2>
					<div class="well">
						<c:if test="${not empty answers }">
							<c:forEach var="answer" items="${answers}">
								<div class="form-group row">
									<div class="col-lg-12">
										<div>${answer.text}</div>
										<div style="font-size:13px;">
										<strong>Confidence: </strong>${answer.confidence}
										</div>
									</div>
								</div>
					        </c:forEach>
						</c:if>
						<c:if test="${empty answers }">
							<div class="col-lg-12">
								No answers for that question, try with another question
							</div> 
						</c:if>
					</div>
				</c:if>
				<c:if test="${not empty error}">
					<h2>Output</h2>
					<div class="well">
						<p style="font-weight:bold;color:red;">Error: ${error}</p>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/css_browser_selector.js"></script>
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="js/example_input.js"></script>
</body>
</html>