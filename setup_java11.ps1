# Run this script as administrator
$javaHome = "C:\Program Files\Eclipse Adoptium\jdk-11.0.21.9-hotspot"

# Set JAVA_HOME
[System.Environment]::SetEnvironmentVariable('JAVA_HOME', $javaHome, [System.EnvironmentVariableTarget]::Machine)

# Add Java to PATH
$path = [System.Environment]::GetEnvironmentVariable('Path', [System.EnvironmentVariableTarget]::Machine)
if ($path -notlike "*$javaHome\bin*") {
    $newPath = "$path;$javaHome\bin"
    [System.Environment]::SetEnvironmentVariable('Path', $newPath, [System.EnvironmentVariableTarget]::Machine)
}

Write-Host "Java 11 environment variables have been set."
Write-Host "Please restart your terminal to apply the changes." 