call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openbrowser
echo.
echo runcrud.bat has errors - breaking script execution
goto end

:openbrowser
start chrome http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Chrome browser cannot be opened
goto fail

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.