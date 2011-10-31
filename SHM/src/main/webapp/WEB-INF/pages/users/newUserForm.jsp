<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form modelAttribute="user" action="add" method="post">
    <fieldset>
        <legend>Account Fields</legend>
        <p>

            <form:hidden path="id"/>
        </p>

        <p>

            <form:input path="name"/>
        </p>

        <p>

            <form:input path="patronymic"/>
        </p>

        <p>


            <form:input path="lastName"/>
        </p>

        <p>


        </p>


        <p>
            <input type="submit"/>
        </p>
    </fieldset>
</form:form>