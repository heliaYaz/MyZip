import java.util.HashMap;

public class SlidingWindow {

    HashMap<String,String> hashMap=new HashMap<>();

    int v;
    int W= (int) Math.pow(2,v);

    int b;
    int B= (int) Math.pow(2,b);

    int limit=0;

    private int i;
    private int j;


    public SlidingWindow(int i,int j,int v,int b){
        this.i=i;
        this.j=j;
        this.v=v;
        this.b=b;
        updateNumbers();
    }


    public void newLine(){
        this.i=0;
        this.j=0;
        hashMap.clear();
    }

    public int getLength(){
        return j-i+1;
    }



    public void updateNumbers(){
        this.W= (int) Math.pow(2,v);
        this.B= (int) Math.pow(2,b);
        this.limit= (int) Math.ceil(((double)v+(double)b)/8);
    }




    public void updateHashMap(int prev_i,int prev_j,String st){
        //deleting
        int i2=prev_i;
        while (i2<i){
            int j2=i2+limit;
            while (j2<=i2+B && j2<=st.length()) {
                hashMap.remove(st.substring(i2, j2));
                j2++;
            }
            i2++;
        }

        //inserting
        int i3=i;
        while(i3<j){
            int j3=Math.max(i3+limit,prev_j+2);
            j3=Math.min(j3,st.length()-1);
            j3=Math.min(j3,j);

            while (j3<=Math.min(j+1,st.length())) {
                String sub=st.substring(i3, j3);
                if(sub.length()>limit && sub.length()<B) {
                    hashMap.put(sub, i3+"");
                }

                j3++;
            }
            i3++;
        }

    }

    public String findMatch(String buffer){

        int index_buffer=buffer.length()-1;
        String longest=buffer;

        int index=0;
        int length=0;

        while (index_buffer>limit){
//            System.out.println(hashMap.toString());
//            System.out.println("find match: "+hashMap.get(longest)+"-"+longest);

            if (hashMap.containsKey(longest)){
                String val=hashMap.get(longest);
                index=Integer.parseInt(val);
                length=longest.length();

                break;
            }
            else{
                index_buffer--;
                longest=longest.substring(0,index_buffer);

            }
        }

        if(index==0 && length==0)
            return "";


        int dist=(j-index);



        // # + length + distance index
        return "#"+ Integer.toString(length,16) +"#"+ Integer.toString(dist,16)+"#";
    }

    public String next_step(String st,String result){
        //expanding window length
        if(getLength()<W){
            if(j<=st.length()-1){
                result=result+st.charAt(j);

                int end=Math.min(j+B,st.length());
                String match=findMatch(st.substring(j+1,end));

                if(match.length()>1){
                    //get value of lengthe matche peida shode
                    int indx2=match.substring(1).indexOf('#')+1;
//                    int indx3=match.substring(indx2+1).indexOf('#')+indx2+1;

                    int k= Integer.valueOf(match.substring(1,indx2),16);

                    //moving j to end of zipped string
                    int prev_j=j;
                    j+=(k)+1;
                    updateHashMap(i,prev_j,st);
                    result=result+match;
                }

                else{
                    this.j++;
                    updateHashMap(i,j-1,st);
                }


            }

            //line ended
            else{
                return result;
            }
        }


        //
        else{
            if(!(getLength()==W)) {
                int prev_i=i;
                i = j - W + 1;
                updateHashMap(prev_i, j, st);
            }


            //shifting search window
            if(j<st.length()-1) {
                result=result+st.charAt(j);

                int end=Math.min(j+B,st.length());
                String match = findMatch(st.substring(j, end));
                if (match.length() >= limit) {

                    //get value of lengthe matche peida shode
                    int indx2=match.substring(1).indexOf('#')+1;

                    int k= Integer.valueOf(match.substring(1,indx2),16);


                    //moving j to end of zipped string
                    int prev_j=j;
                    j += (k)+1;
                    updateHashMap(i,prev_j,st);


                    result=result+match;
                }

                else {
                    this.i++;
                    this.j++;
                    updateHashMap(i-1,j-1,st);

                }

            }


            else
                return result;


        }


        return result;
    }


    public String zip_line(String line){
        String result="";

        while(!(j>line.length()-1)){
            result=next_step(line,result);
        }

        return result;
    }


    public String unzip_line(String line){
        String result="";

        int i=0;
        int j_res=0;
        while(i<line.length()){
            //
            if(line.charAt(i)!='#'){
                result= result + line.charAt(i);
                i++;
                j_res++;
            }

            //unzipping
            else{
                String st=line.substring(i);

                int indx2=st.substring(1).indexOf('#')+1;
                int indx3=st.substring(indx2+1).indexOf('#')+indx2+1;

                int a=Integer.valueOf(st.substring(1,indx2),16);
                int t=Integer.valueOf(st.substring(indx2+1,indx3),16);


                String repeated=result.substring(j_res-t-1,j_res-t+a-1);

                result=result+repeated;

                i=i+indx3+1;
                j_res+=a;
            }

        }

        return result;
    }



}
