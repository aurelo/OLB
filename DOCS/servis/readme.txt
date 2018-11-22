

Instalacija servisa (sve kopiraj u cmd):


prunsrv //IS//AServisTest01 --DisplayName="A Servis Test 01"^
        --Install=C:\test\java_service\prunsrv.exe^
        --Jvm="C:\Program Files (x86)\Java\jre7\bin\client\jvm.dll"^
        --Startup=auto^
        --Classpath=C:\test\java_service\jars\*^
        --StartMode=jvm^
        --StartClass=service.ServiceTest^
        --StartMethod=windowsService^
        --StartParams=start^
        --StopMode=jvm^
        --StopClass=service.ServiceTest^
        --StopMethod=windowsService^
        --StopParams=stop^
        --LogPath=c:\test\java_service\log^
        --LogPrefix=log-ServisTest01^
        --LogLevel=Error^
        --StdOutput=auto^
        --StdError=auto










Deinstalacija servisa:

prunsrv //DS//AServisTest01
