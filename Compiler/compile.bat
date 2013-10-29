@echo off

rem change directory to the github directory
cd "../.."
set VERSION=%1

rem copy the sourcecode into the mcp
xcopy "CraftingPillar\me" "MCP 1.6.4\mcp\src\minecraft\me\" /S /Y
rem running mcp
cd "MCP 1.6.4\mcp"
runtime\bin\python\python_mcp runtime\recompile.py %*
runtime\bin\python\python_mcp runtime\reobfuscate.py %*
cd "..\.."
rem deleting sources from the mcp
del "MCP 1.6.4\mcp\src\minecraft\me\dawars" /F /Q /S

rem outputting for upload
xcopy "MCP 1.6.4\mcp\reobf\minecraft\me" "CraftingPillar\Version-%VERSION%\me\" /S /Y
xcopy "CraftingPillar\assets" "CraftingPillar\Version-%VERSION%\assets\" /S /Y