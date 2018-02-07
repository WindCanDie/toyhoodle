 namespace java thrift.iface

 service Service {
   bool selfExamine()

   bool regist(1:string host,2:i32  post,3:string name)

   map<string,map<string,string>> agentConf(1:string id)
 }