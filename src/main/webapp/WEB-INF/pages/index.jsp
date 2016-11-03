<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>MyCollection</title>
        <c:url value="/images/favicon.ico" var="favicon_Url" />
        <link rel="shortcut icon" href="${favicon_Url}" type="image/ICO">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

        <style type="text/css">
            .main-info {width: 600px; margin: auto;}
            .img-rounded {width: 200px; cursor:pointer; display:inline; border: 4px double #337ab7;}
            .img-rounded:focus {width: auto; z-index:100; border: 4px double #337ab7;}
            td {padding: 10px; line-height: 1.3;}
        </style>
    </head>
    <body>

        <!-- current user name-->
        <div align="right">
            <c:set var="user_login" scope="page" value="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.name}" />
            <c:url value="/login" var="login_Url" />
            <c:if test="${user_login eq null}">
                <h4>Hello, <a href="${login_Url}"><span class="glyphicon glyphicon-user"></span> Guest </a>!</h4>
            </c:if>
            <c:url value="/my_profile" var="my_profile_Url" />
            <c:if test="${user_login ne null}">
                <h4>Hello, <a href="${my_profile_Url}"> <span class="glyphicon glyphicon-user"></span> <c:out value="${user_login}"/> </a>!</h4>
            </c:if>
        </div>

        <div align="center">

            <!-- header-->
            <h1>
                <c:url value="/images/index.png" var="index_Url" />
                <img src="${index_Url}" width="95" height="75" alt="index" />
                Welcome to MyCollection service!
            </h1>

            <!-- MENU BUTTONS-->
            <c:url value="/view_collections" var="view_collections_Url" />
            <a href="${view_collections_Url}"><button type="button" class="btn btn-primary">
                <span class="glyphicon glyphicon-list"></span> Collections
            </button></a>
            <c:url value="/view_collectors" var="view_collectors_Url" />
            <a href="${view_collectors_Url}"><button type="button" class="btn btn-primary">
                <span class="glyphicon glyphicon-user"></span> Collectors
            </button></a>
            <c:if test="${user_login eq null}">
                <a href="${login_Url}"><button type="button" class="btn btn-primary">
                    <span class="glyphicon glyphicon-ok-sign"></span> Login
                </button></a>
                <c:url value="/registration" var="registration_Url" />
                <a href="${registration_Url}"><button type="button" class="btn btn-primary">
                    <span class="glyphicon glyphicon-pencil"></span> Registration
                </button></a>
            </c:if>
            <c:if test="${user_login ne null}">
                <c:url value="/my_collections" var="my_collections_Url" />
                <a href="${my_collections_Url}"><button type="button" class="btn btn-primary">
                    <span class="glyphicon glyphicon-wrench"></span> My collections
                </button></a>
                <a href="${my_profile_Url}"><button type="button" class="btn btn-primary">
                    <span class="glyphicon glyphicon-wrench"></span> My profile
                </button></a>
                <c:url value="/add_collection" var="add_collection_Url" />
                <a href="${add_collection_Url}"><button type="button" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus"></span> Add Collection
                </button></a>
                <c:url value="/logout" var="logout_Url" />
                <a href="${logout_Url}"><button type="button" class="btn btn-primary">
                    <span class="glyphicon glyphicon-remove-sign"></span> Logout
                </button></a>
            </c:if><br><br>

            <!--SEARCH-->
            <c:if test="${(action ne null) and (action ne 'my_profile')}">
                <c:url value="/search" var="search_Url" />
                <form action="${search_Url}" method="GET" role="form" class="form-inline">
                    <input type="text" name="search" placeholder="Search" value="${search}" class="form-control">
                    <input type="hidden" name="action" value="${action}">
                    <c:if test="${action eq 'view_collection'}">
                        <input type="hidden" name="collection_id" value="${collection.id}">
                    </c:if>
                    <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-search"></span> Submit</button>
                </form>
            </c:if>

            <!--message if user logout-->
            <c:if test="${param.logout ne null}">
                <div class="alert alert-info" role="alert">Thanks for coming!</div>
            </c:if>

            <!--no photo url-->
            <c:url value="/images/noPhoto.png" var="noPhoto_Url" />

            <!--depending on the parameter action view: MAIN PAGE / PROFILE / COLLECTORS / COLLECTIONS / COLLECTION-->
            <c:choose>

                <c:when test="${action eq null}"> <!--MAIN PAGE-->
                    <h4>Hi, my name is Vitalii Voronov, I am beginner Java Developer, and this is my training project.</h4>
                    <strong>Project idea:</strong><br>
                    The service is intended for people who collect anything, and want show collection to friends or just organize off collection.<br><br>
                    <strong>Project used:</strong><br>
                    <div class="main-info" align="left">
                            <strong>Back-end:</strong><br>
                            - Maven build manager;<br>
                            - Hibernate(JPA) framework for object relational mapping and query databases using SQL;<br>
                            - Spring Security framework for providing authentication and authorization;<br>
                            - Spring Data framework for easy to implement JPA-based repositories<br>
                            - Spring Boot framework for automatically configure, create applications with embed Tomcat<br>
                            - Tomcat servlet container<br>
                            - MySQL Database server<br>
                            <strong>Front-end:</strong><br>
                            - html, css, Bootstrap<br>
                            - Java Script, jQuery<br>
                    </div><br>
                    <strong>For testing you can create your account or use this test accounts:</strong> <br>
                        login:"<strong>admin</strong>" - password:"<strong>password</strong>";<br>
                        login:"<strong>user</strong>" - password:"<strong>password</strong>";<br><br>
                    <strong>Source code hear:</strong><br>
                    <a href="https://github.com/VitaliiVK">github.com/VitaliiVK</a><br><br>
                </c:when>

                <c:when test="${action eq 'my_profile'}"> <!-- PROFILE-->
                    <c:if test="${collector ne null}">
                        <!--urls-->
                        <c:url value="/my_profile" var="URL_my_profile" />
                        <c:url value="/view_collection/id_${collection.id}" var="view_collection_URL" />
                        <c:url value="/photo/collector/${collector.id}" var="collector_photo_URL" />
                        <!--form for update or remove-->
                        <form action="${URL_my_profile}" enctype="multipart/form-data" method="POST" class="form-horizontal">
                            <table>
                                <tr>
                                    <td align="center">
                                        <strong>Login: </strong><c:out value="${collector.login}"/><br><br>
                                        <strong>Collection list:</strong><br>
                                        <c:forEach items="${collector.collectionsList}" var="collection">
                                            <a href="${view_collection_URL}"><c:out value="${collection.name}"/></a><br>
                                        </c:forEach><br>
                                        <strong>Profile photo:</strong><br>
                                        <img src="${collector_photo_URL}" tabindex="1" alt="index" onerror="this.src ='${noPhoto_Url}'" class="img-rounded" /><br><br>
                                        <input type="file" name="photo">
                                    </td>
                                    <td align="center">
                                        <strong>Name: </strong>
                                        <input type="text" name="name" value="${collector.name}" class="form-control">
                                        <strong>Surname: </strong>
                                        <input type="text" name="surname" value="${collector.surname}" class="form-control">
                                        <strong>Email: </strong>
                                        <input type="text" name="email" value="${collector.email}" class="form-control">
                                        <strong>Phone: </strong>
                                        <input type="text" name="phone" value="${collector.phone}" class="form-control">
                                        <strong>Country: </strong>
                                        <input type="text" name="country" value="${collector.country}" class="form-control">
                                        <strong>City: </strong>
                                        <input type="text" name="city" value="${collector.city}" class="form-control">
                                        <strong>Specialization: </strong>
                                        <input type="text" name="specialization" value="${collector.specialization}" class="form-control">
                                        <strong>Information: </strong><br>
                                        <textarea name="information" cols="40" rows="5" class="form-control"><c:out value="${collector.information}"/></textarea>
                                        <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> Update profile</button>
                                   </td>
                                </tr>
                            </table>
                        </form>
                    </c:if>
                </c:when>

                <c:when test="${action eq 'view_collectors'}"> <!--COLLECTORS-->
                    <c:choose>
                        <c:when test="${collectorsList.size() ne 0}">
                            <h3><span class="glyphicon glyphicon-user"></span> Collectors list: </h3>
                                <table>
                                    <c:forEach items="${collectorsList}" var="collector">
                                        <!--variables-->
                                        <c:set var="collectorName" value="${collector.name}"/>
                                        <c:set var="collectorSurname" value="${collector.surname}"/>
                                        <c:set var="collectorEmail" value="${collector.email}"/>
                                        <c:set var="collectorPhone" value="${collector.phone}"/>
                                        <c:set var="collectorCountry" value="${collector.country}"/>
                                        <c:set var="collectorCity" value="${collector.city}"/>
                                        <c:set var="collectorSpecialization" value="${collector.specialization}"/>
                                        <c:set var="collectorInformation" value="${collector.information}"/>
                                        <c:set var="collectorCollectionsList" value="${collector.collectionsList}"/>
                                        <!--urls-->
                                        <c:url value="/view_collection/id_${collection.id}" var="view_collection_URL" />
                                        <c:url value="/photo/collector/${collector.id}" var="photo_collector_URL" />
                                        <tr>
                                            <td align="center"><img src="${photo_collector_URL}" width="200" tabindex="1" alt="collector photo"
                                                                    onerror="this.src ='${noPhoto_Url}'" class="img-rounded" /></td>
                                            <td>
                                                <!--login-->
                                                <strong><span class="glyphicon glyphicon-user"></span> Login: </strong><c:out value="${collector.login}"/><br>
                                                <!--name-->
                                                <c:if test="${(collectorName ne null) and (collectorName ne '')}">
                                                    <strong><span class="glyphicon glyphicon-pencil"></span> Name: </strong><c:out value="${collectorName}"/><br>
                                                </c:if>
                                                <!--surname-->
                                                <c:if test="${(collectorSurname ne null) and (collectorSurname ne '')}">
                                                    <strong><span class="glyphicon glyphicon-pencil"></span> Surname: </strong><c:out value="${collectorSurname}"/><br>
                                                </c:if>
                                                <!--email-->
                                                <c:if test="${(collectorEmail ne null) and (collectorEmail ne '')}">
                                                    <strong><span class="glyphicon glyphicon-envelope"></span> Email: </strong><c:out value="${collectorEmail}"/><br>
                                                </c:if>
                                                <!--phone-->
                                                <c:if test="${(collectorPhone ne null) and (collectorPhone ne '')}">
                                                    <strong><span class="glyphicon glyphicon-earphone "></span> Phone: </strong><c:out value="${collectorPhone}"/><br>
                                                </c:if>
                                                <!--country-->
                                                <c:if test="${(collectorCountry ne null) and (collectorCountry ne '')}">
                                                    <strong><span class="glyphicon glyphicon-map-marker "></span> Country: </strong><c:out value="${collectorCountry}"/><br>
                                                </c:if>
                                                <!--city-->
                                                <c:if test="${(collectorCity ne null) and (collectorCity ne '')}">
                                                    <strong><span class="glyphicon glyphicon-map-marker "></span> City: </strong><c:out value="${collectorCity}"/><br>
                                                </c:if>
                                                <!--specialization-->
                                                <c:if test="${(collectorSpecialization ne null) and (collectorSpecialization ne '')}">
                                                    <strong><span class="glyphicon glyphicon-pencil"></span> Specialization: </strong><c:out value="${collectorSpecialization}"/><br>
                                                </c:if>
                                                <!--information-->
                                                <c:if test="${(collectorInformation ne null) and (collectorInformation ne '')}">
                                                    <strong><span class="glyphicon glyphicon-info-sign"></span> Information:</strong>
                                                    <div style="white-space:pre-wrap; max-width: 500px; margin-left: 20px;" ><c:out value="${collectorInformation}"/></div>
                                                </c:if>
                                                <!--collections list-->
                                                <strong><span class="glyphicon glyphicon-th-list"></span> Collections list:</strong>
                                                <div style="margin-left: 20px;">
                                                    <c:forEach items="${collectorCollectionsList}" var="collection">
                                                        <a href="${view_collection_URL}">
                                                            <c:out value="${collection.name}"/></a><br>
                                                    </c:forEach>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                        </c:when>
                        <c:otherwise>
                            <h3>Collectors not found!</h3>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <c:when test="${(action eq 'view_collections') or (action eq 'my_collections')}"> <!--COLLECTIONS-->
                    <c:choose>
                        <c:when test="${collectionsList.size() ne 0}">
                            <h3><span class="glyphicon glyphicon-th-list"></span> Collections list: </h3>
                            <table>
                                <!--collections list-->
                                <c:forEach items="${collectionsList}" var="collection">
                                    <!--variables-->
                                    <c:set var="commentsList" value="${collection.commentsList}"/>
                                    <c:set var="commentsCount" value="${commentsList.size()}"/>
                                    <c:set var="likeButtonIconClass" value="glyphicon glyphicon-heart-empty"/>
                                    <c:set var="likeList" value="${collection.likeList}"/>
                                    <c:forEach items="${likeList}" var="like">
                                        <c:if test="${like.collector.login eq user_login}">
                                            <c:set var="likeButtonIconClass" value="glyphicon glyphicon-heart"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:set var="likeCount" value="${likeList.size()}"/>
                                    <c:set var="collectionId" value="${collection.id}"/>
                                    <c:set var="collectionName" value="${collection.name}"/>
                                    <c:set var="collectionType" value="${collection.type}"/>
                                    <c:set var="collectionInformation" value="${collection.information}"/>
                                    <!--urls-->
                                    <c:url value="/update_remove_collection" var="URL_update_remove_collection" />
                                    <c:url value="/search?search=${collection.collector.login}&action=view_collectors" var="URL_collector" />
                                    <c:url value="/view_collection/id_${collectionId}" var="view_collection_URL" />
                                    <c:url value="/add_remove_like" var="add_remove_like_URL" />
                                    <c:url value="/add_comment" var="add_comment_URL" />
                                    <c:url value="/photo/collection/${collectionId}" var="collection_photo_URL" />
                                    <tr>
                                        <td align="center"> <!--photo-->
                                            <a href="${view_collection_URL}">
                                                <img src="${collection_photo_URL}" width="200" alt="collection photo" onerror="this.src ='${noPhoto_Url}'" class="img-rounded" />
                                            </a>
                                        </td>
                                        <td>
                                            <!--form for update or remove-->
                                            <form action="${URL_update_remove_collection}" enctype="multipart/form-data" method="POST">
                                                <!--new photo-->
                                                <div id="photo${collectionId}" style="display:none;"><strong>Photo:</strong><input type="file" name="photo"></div><br>
                                                <!--name-->
                                                <strong><span class="glyphicon glyphicon-pencil"></span> Name: </strong><c:out value="${collectionName}"/>
                                                <div id="name${collectionId}" style="display:none;">
                                                    <input type="text" name="name" value="${collectionName}" class="form-control">
                                                </div><br>
                                                <!--type-->
                                                <strong><span class="glyphicon glyphicon-tag"></span> Type: </strong><c:out value="${collectionType}"/>
                                                <div id="type${collectionId}" style="display:none;">
                                                    <input type="text" name="type" value="${collectionType}" class="form-control">
                                                </div><br>
                                                <!--collector-->
                                                <strong><span class="glyphicon glyphicon-user"></span> Collector: </strong>
                                                <a href="${URL_collector}"><c:out value="${collection.collector.login}"/></a><br>
                                                <!--Like-->
                                                <strong><span class="glyphicon glyphicon-heart"></span> Like:</strong>
                                                <div id="likeCount${collectionId}" style="display:inline-block;"><c:out value="${likeCount}"/></div> <br>
                                                <!--Comments-->
                                                <strong><span class="glyphicon glyphicon-comment"></span> Comments:</strong>
                                                <div id="commentsCount${collectionId}" style="display:inline-block;"><c:out value="${commentsCount}"/></div> <br>
                                                <!--information-->
                                                <c:if test="${(collectionInformation ne null) and (collectionInformation ne '')}">
                                                    <strong><span class="glyphicon glyphicon-info-sign"></span> Information: </strong>
                                                    <div style="white-space:pre-wrap; max-width: 500px; margin-left: 20px;"><c:out value="${collectionInformation}"/></div>
                                                </c:if>
                                                <div id="info${collectionId}" style="display:none;">
                                                    <strong>Information: </strong><br>
                                                    <textarea name="info" cols="40" rows="5" class="form-control"><c:out value="${collection.information}"/></textarea><br>
                                                </div>
                                                <!--buttons-->
                                                <div id="view${collectionId}" style="display:inline-block;">
                                                    <!--View collection Button-->
                                                    <a href="${view_collection_URL}">
                                                        <button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-picture"></span> View collection</button>
                                                    </a>

                                                    <div id="likeComment${collectionId}" style="display:inline-block;">
                                                        <!--Like Button-->
                                                        <button id="likeButton${collectionId}" type="button" class="btn btn-primary" onclick="addRemoveLike('likeButton${collectionId}',
                                                                'likeCount${collectionId}',
                                                                '${user_login}',
                                                                'like_error_message${collectionId}',
                                                                'likeForm${collectionId}',
                                                                'hidden_like_action${collectionId}',
                                                                '${add_remove_like_URL}')"><span class="${likeButtonIconClass}"></span> Like</button>
                                                        <!--View comments Button-->
                                                        <a href="javascript:viewComment('comments','${collectionId}');" id="viewComments${collectionId}">
                                                            <button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-comment"></span> View comments</button>
                                                        </a>
                                                    </div>
                                                </div>
                                                <c:if test="${(collection.collector.login eq user_login) and (action eq 'my_collections')}">
                                                    <!--hidden fields-->
                                                    <input type="hidden" name="collection_id" value="${collection.id}">
                                                    <input type="hidden" name="action" value="remove" id="action${collection.id}">
                                                    <!--Edit Button-->
                                                    <a href="javascript:onOff('name','${collectionId}'),
                                                                        onOff('info','${collectionId}'),
                                                                        onOff('view','${collectionId}'),
                                                                        onOff('photo','${collectionId}'),
                                                                        onOff('type','${collectionId}'),
                                                                        onOff('update','${collectionId}'),
                                                                        onOff('remove','${collectionId}');"
                                                                        id="${collectionId}">
                                                        <button type="button" class="btn btn-success"><span class="glyphicon glyphicon-wrench"></span> Edit</button>
                                                    </a>
                                                    <!--Update Button-->
                                                    <div id="update${collectionId}" style="display:none;">
                                                        <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> Update</button>
                                                    </div>
                                                    <!--Remove Button-->
                                                    <div id="remove${collectionId}" style="display:inline-block;">
                                                        <button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span> Remove</button>
                                                    </div>
                                                </c:if><br>
                                            </form>
                                            <!--Like error-->
                                            <div id="like_error_message${collectionId}" class="alert alert-danger" role="alert" style="display:none;">
                                                You must be logged to click Like!
                                            </div>
                                            <!--hidden Like form-->
                                            <form action="" method="POST" id="likeForm${collectionId}" onsubmit="return false" class="form-inline">
                                                <input type="hidden" name="instance_or_collection" value="collection">
                                                <input type="hidden" name="id" value="${collectionId}">
                                                <input type="hidden" name="user_login" value="${user_login}">
                                                <input id="hidden_like_action${collectionId}" type="hidden" name="like_action" value="add_like">
                                            </form>
                                            <!--Comments block-->
                                            <div id="comments${collectionId}" style="display:none;">
                                                <strong>Comments:</strong><br>
                                                <c:forEach items="${commentsList}" var="comment">
                                                    <strong><c:out value="${comment.collector.login}"/>:</strong> <c:out value="${comment.text}"/><br>
                                                </c:forEach>
                                                <div id="newComment${collectionId}"></div>
                                                <c:if test="${user_login ne null}">
                                                    <!--Comment form-->
                                                    <form action="" method="POST" id="commentForm${collectionId}" onsubmit="return false" class="form-inline">
                                                        <input type="text" name="comment_text"  class="form-control" placeholder="comment" autocomplete="off"/>
                                                        <!--Send comment Button-->
                                                        <button type="submit" class="btn btn-success" onclick="addComment('newComment${collectionId}',
                                                                'commentsCount${collectionId}',
                                                                'commentForm${collectionId}',
                                                                '${add_comment_URL}',
                                                                '${user_login}')">
                                                            <span class="glyphicon glyphicon-ok"></span> Send
                                                        </button>
                                                        <input type="hidden" name="instance_or_collection" value="collection">
                                                        <input type="hidden" name="id" value="${collectionId}">
                                                        <input type="hidden" name="user_login" value="${user_login}">
                                                    </form>
                                                </c:if>
                                                <c:if test="${user_login eq null}">
                                                    *you must be logged to send your comments
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <br>
                        </c:when>
                        <c:otherwise>
                            <h3>Collections not found!</h3>
                            <c:if test="${action eq 'my_collections'}">
                                <p>Click the <b>"+ Add Collection"</b> to create new collection</p>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <c:when test="${action eq 'view_collection'}"> <!--COLLECTION-->
                    <c:choose>
                        <c:when test="${instancesList.size() ne 0}">
                            <!--urls-->
                            <c:url value="/add_remove_like" var="add_remove_like_URL" />
                            <c:url value="/update_remove_instance" var="URL_update_remove_instance" />
                            <c:url value="/search?search=${collection.collector.login}&action=view_collectors" var="URL_collector" />
                            <c:url value="/add_comment" var="add_comment_URL" />
                            <!--collection information-->
                            <strong>Collection: </strong><c:out value="${collection.name}"/><br>
                            <strong>Type: </strong><c:out value="${collection.type}"/><br>
                            <strong>Collector: </strong><a href="${URL_collector}"><c:out value="${collection.collector.login}"/></a><br>
                            <strong>Information: </strong> <div style="white-space:pre-wrap; max-width: 500px;"><c:out value="${collection.information}"/></div><br>
                            <!--instances list-->
                            <table>
                                <c:forEach items="${instancesList}" var="instance">
                                    <!--variables-->
                                    <c:set var="commentsList" value="${instance.commentsList}"/>
                                    <c:set var="commentsCount" value="${commentsList.size()}"/>
                                    <c:set var="likeButtonIconClass" value="glyphicon glyphicon-heart-empty"/>
                                    <c:set var="likeList" value="${instance.likeList}"/>
                                    <c:forEach items="${likeList}" var="like">
                                        <c:if test="${like.collector.login eq user_login}">
                                            <c:set var="likeButtonIconClass" value="glyphicon glyphicon-heart"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:set var="likeCount" value="${likeList.size()}"/>
                                    <c:set var="instanceId" value="${instance.id}"/>
                                    <c:set var="instanceName" value="${instance.name}"/>
                                    <c:if test="${(instanceName eq null) or (instanceName eq '')}">
                                        <c:set var="instanceName" value="no name"/>
                                    </c:if>
                                    <c:set var="instanceSubtype" value="${instance.subtype}"/>
                                    <c:if test="${(instanceSubtype eq null) or (instanceSubtype eq '')}">
                                        <c:set var="instanceSubtype" value="no subtype"/>
                                    </c:if>
                                    <c:set var="instanceInformation" value="${instance.information}"/>
                                    <c:if test="${(instanceInformation eq null) or (instanceInformation eq '')}">
                                        <c:set var="instanceInformation" value="no information"/>
                                    </c:if>
                                        <tr>
                                            <td align="center"> <!--Photo-->
                                                <c:url value="/photo/instance/${instanceId}" var="photo_instance_URL" />
                                                <img src="${photo_instance_URL}" alt="instance photo" tabindex="1" onerror="this.src ='${noPhoto_Url}'" class="img-rounded" /><br>
                                            </td>
                                            <td>
                                                <!--form for update or remove-->
                                                <form action="${URL_update_remove_instance}" enctype="multipart/form-data" method="POST" >
                                                    <!--new photo-->
                                                    <div id="photo${instanceId}" style="display:none;"><strong>New photo:</strong><input type="file" name="photo"></div><br>
                                                    <!--Name-->
                                                    <strong><span class="glyphicon glyphicon-pencil"></span> Name: </strong><c:out value="${instanceName}"/>
                                                    <div id="name${instanceId}" style="display:none;">
                                                        <input type="text" name="name" value="${instanceName}" class="form-control">
                                                    </div><br>
                                                    <!--Subtype-->
                                                    <strong><span class="glyphicon glyphicon-tags"></span> Subtype: </strong><c:out value="${instanceSubtype}"/>
                                                    <div id="subtype${instanceId}" style="display:none;">
                                                        <input type="text" name="subtype" value="${instanceSubtype}" class="form-control">
                                                    </div><br>
                                                    <!--Like-->
                                                    <strong><span class="glyphicon glyphicon-heart"></span> Like:</strong>
                                                    <div id="likeCount${instanceId}" style="display:inline-block;"><c:out value="${likeCount}"/></div> <br>
                                                    <!--Comments-->
                                                    <strong><span class="glyphicon glyphicon-comment"></span> Comments:</strong>
                                                    <div id="commentsCount${instanceId}" style="display:inline-block;"><c:out value="${commentsCount}"/></div> <br>
                                                    <!--Information-->
                                                    <strong><span class="glyphicon glyphicon-info-sign"></span> Information: </strong>
                                                    <div style="white-space:pre-wrap; max-width: 500px; margin-left: 20px;"><c:out value="${instanceInformation}"/></div>
                                                    <div id="info${instanceId}" style="display:none;">
                                                        <textarea name="info" cols="40" rows="5" class="form-control"><c:out value="${instanceInformation}"/></textarea>
                                                    </div>
                                                    <!--Buttons-->
                                                    <div id="likeComment${instanceId}" style="display:inline-block;">
                                                        <!--Like Button-->
                                                        <button id="likeButton${instanceId}" type="button" class="btn btn-primary"
                                                                onclick="addRemoveLike('likeButton${instanceId}',
                                                                                       'likeCount${instanceId}',
                                                                                       '${user_login}',
                                                                                       'like_error_message${instanceId}',
                                                                                       'likeForm${instanceId}',
                                                                                       'hidden_like_action${instanceId}',
                                                                                       '${add_remove_like_URL}')"><span class="${likeButtonIconClass}"></span> Like</button>
                                                        <!--View comments Button-->
                                                        <a href="javascript:viewComment('comments','${instanceId}');" id="viewComments${instanceId}">
                                                            <button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-comment"></span> View comments</button>
                                                        </a>
                                                    </div>
                                                    <c:if test="${collection.collector.login eq user_login}">
                                                        <!--hidden fields-->
                                                        <input type="hidden" name="instance_id" value="${instanceId}">
                                                        <input type="hidden" name="action" value="remove" id="action${instanceId}"> <!--action = remove or edit-->
                                                        <!--Edit Button-->
                                                        <a href="javascript:onOff('name','${instanceId}'),
                                                                            onOff('subtype','${instanceId}'),
                                                                            onOff('info','${instanceId}'),
                                                                            onOff('photo','${instanceId}'),
                                                                            onOff('update','${instanceId}'),
                                                                            onOff('likeComment','${instanceId}'),
                                                                            onOff('remove','${instanceId}');"
                                                                            id="${instanceId}">
                                                            <button type="button" class="btn btn-success"><span class="glyphicon glyphicon-wrench"></span> Edit</button>
                                                        </a>
                                                        <!--Update Button-->
                                                        <div id="update${instanceId}" style="display:none;">
                                                            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> Update</button>
                                                        </div>
                                                        <!--Remove Button-->
                                                        <div id="remove${instanceId}" style="display:inline-block;">
                                                            <button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span> Remove</button>
                                                        </div>
                                                    </c:if>
                                                </form>
                                                <!--Like error-->
                                                <div id="like_error_message${instanceId}" class="alert alert-danger" role="alert" style="display:none;">
                                                    You must be logged to click Like!
                                                </div>
                                                <!--hidden Like form-->
                                                <form action="" method="POST" id="likeForm${instanceId}" onsubmit="return false" class="form-inline">
                                                    <input type="hidden" name="instance_or_collection" value="instance">
                                                    <input type="hidden" name="id" value="${instanceId}">
                                                    <input type="hidden" name="user_login" value="${user_login}">
                                                    <input id="hidden_like_action${instanceId}" type="hidden" name="like_action" value="add_like">
                                                </form>
                                                <!--Comments block-->
                                                <div id="comments${instanceId}" style="display:none;">
                                                    <strong>Comments:</strong><br>
                                                    <c:forEach items="${commentsList}" var="comment">
                                                        <strong><c:out value="${comment.collector.login}"/>:</strong> <c:out value="${comment.text}"/><br>
                                                    </c:forEach>
                                                    <div id="newComment${instanceId}"></div>
                                                    <c:if test="${user_login ne null}">
                                                        <!--Comment form-->
                                                        <form action="" method="POST" id="commentForm${instanceId}" onsubmit="return false" class="form-inline">
                                                            <input type="text" name="comment_text"  class="form-control" placeholder="comment" autocomplete="off"/>
                                                            <!--Send comment Button-->
                                                            <button type="submit" class="btn btn-success"
                                                                    onclick="addComment('newComment${instanceId}',
                                                                                        'commentsCount${instanceId}',
                                                                                        'commentForm${instanceId}',
                                                                                        '${add_comment_URL}',
                                                                                        '${user_login}')">
                                                                <span class="glyphicon glyphicon-ok"></span> Send
                                                            </button>
                                                            <input type="hidden" name="instance_or_collection" value="instance">
                                                            <input type="hidden" name="id" value="${instanceId}">
                                                            <input type="hidden" name="user_login" value="${user_login}">
                                                        </form>
                                                    </c:if>
                                                    <c:if test="${user_login eq null}">
                                                        *you must be logged to send your comments
                                                    </c:if>
                                                </div>
                                            </td>
                                        </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <h3>Instances not found!</h3>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${collection.collector.login eq user_login}"><!--add instance-->
                        <h4>New instance:</h4>
                        <c:url value="/add_instance" var="add_instance_URL" />
                        <form action="${add_instance_URL}" enctype="multipart/form-data" method="POST" role="form" class="form-horizontal">
                            <table>
                                <tr>
                                    <td>
                                        Name: <input type="text" name="name"class="form-control">
                                        Subtype: <input type="text" name="subtype"class="form-control">
                                        Photo*: <input type="file" name="photo">
                                    </td>
                                    <td>
                                        Information: <br><textarea name="information" cols="40" rows="5" class="form-control"></textarea>
                                    </td>
                                </tr>
                            </table>
                            <input type="hidden" name="collection_id" value="${collection.id}">
                            <input type="submit" value="+ Add instance" class="btn btn-success"/><br>
                        </form>
                        * photo format .jpeg
                    </c:if>
                </c:when>

            </c:choose>

        </div>


        <script>

            function onOff(fild,id){
                p=document.getElementById(fild+id);
                x=document.getElementById(id);
                y=document.getElementById("action"+id);
                if(p.style.display=="none"){
                    x.innerHTML="<button type=\"button\" class=\"btn btn-success\"><span class=\"glyphicon glyphicon-wrench\"></span> Edit</button>";
                    p.style.display="inline-block";
                    y.value = "remove";
                }
                else{
                    x.innerHTML="<br><button type=\"button\" class=\"btn btn-primary\"><span class=\"glyphicon glyphicon-remove\"></span> Cancel</button>";
                    p.style.display="none";
                    y.value = "edit";
                }
            }

            function viewComment(fild,id){
                p=document.getElementById(fild+id);
                x=document.getElementById("viewComments"+id);
                y=document.getElementById("action"+id);
                if(p.style.display=="none"){
                    x.innerHTML="<button type=\"button\" class=\"btn btn-primary\"><span class=\"glyphicon glyphicon-remove\"></span> Hide comments</button>";
                    p.style.display="inline-block";
                    y.value = "remove";
                }
                else{
                    x.innerHTML="<button type=\"button\" class=\"btn btn-primary\"><span class=\"glyphicon glyphicon-comment\"></span> View comments</button>";
                    p.style.display="none";
                    y.value = "addComments";
                }
            }

            function addComment(result,count,form,url,user) {
                comment = $(':input','#'+form).val();
                if(comment=='')return;
                document.getElementById(count).innerHTML = parseInt(document.getElementById(count).innerHTML, 10)+1;
                jQuery.ajax({
                    url: url,
                    type: "POST",
                    dataType: "html",
                    data: jQuery("#"+form).serialize(),
                    success: function() {
                        document.getElementById(result).innerHTML+="<strong>"+user+": </strong>" + comment + "<br>";
                    },
                    error: function(response) {
                        document.getElementById(result).innerHTML += "comment add error!" + response;
                    }
                });
                $(':input','#'+form)
                        .not(':button, :submit, :reset, :hidden')
                        .val('');
            }

            function addRemoveLike(buttonId,liceCount,user_login,error_message,likeForm,likeAction,formURL) {
                if(user_login==''){
                    document.getElementById(error_message).style.display="block";
                    return;
                }
                action = document.getElementById(likeAction);
                count = parseInt(document.getElementById(liceCount).innerHTML, 10);
                if(document.getElementById(buttonId).innerHTML == '<span class="glyphicon glyphicon-heart-empty"></span> Like'){
                    action.value = "add_like";
                    jQuery.ajax({
                        url: formURL,
                        type: "POST",
                        dataType: "html",
                        data: jQuery("#"+likeForm).serialize(),
                        success: function() {
                            document.getElementById(buttonId).innerHTML = '<span class="glyphicon glyphicon-heart"></span> Like';
                            document.getElementById(liceCount).innerHTML = count+1;
                        },
                        error: function(response) {
                            document.getElementById(error_message).innerHTML = "DbLike error!" + response;
                        }
                    });

                }
                else{
                    action.value = "remove_like";
                    jQuery.ajax({
                        url: formURL,
                        type: "POST",
                        dataType: "html",
                        data: jQuery("#"+likeForm).serialize(),
                        success: function() {
                            document.getElementById(buttonId).innerHTML = '<span class="glyphicon glyphicon-heart-empty"></span> Like';
                            document.getElementById(liceCount).innerHTML = count-1;
                        },
                        error: function(response) {
                            document.getElementById(error_message).innerHTML = "DbLike error!" + response;
                        }
                    });
                }
            }

        </script>

    </body>
</html>
