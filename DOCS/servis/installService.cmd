"C:\Java\servis\prunsrv.exe" //IS//MbuKabaClient --DisplayName="MbuKabaClient" --Description="KABA OLB klijent"^
        --Install="C:\Java\servis\prunsrv.exe"^
        --Jvm="C:\Java\jre1.8.0_25\bin\server\jvm.dll"^
        --JavaHome="C:\Java\jre1.8.0_25"^
        --Startup=auto^
        --Classpath="C:\java\servis\kaba-client\*"^
        --StartMode=jvm^
        --StartClass=hr.kaba.olb.client.kaba.App^
        --StartMethod=start^
        --StartParams=start^
        --StopMode=jvm^
        --StopClass=hr.kaba.olb.client.kaba.App^
        --StopMethod=stop^
        --StopParams=stop^
        --StartPath="C:\java\servis\kaba-client-1.0"^
        --LogPath="C:\java\servis\kaba-client\logs"^
        --LogPrefix=log-^
        --LogLevel=Error^
        --StdOutput=auto^
        --StdError=auto