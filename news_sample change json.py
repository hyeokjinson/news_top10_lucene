import json
from collections import OrderedDict
import  pandas as pd

with open('news_sample.dat','r',encoding='utf-8') as f:
    news_data=f.read()

news_data=news_data.split('<CREATE_DATE>')[:1001]


# for idx,news in enumerate(news_data):
#     news=news.replace('\n','').replace('\r','')
#     create_d=news.split('<TITLE>')[0]
#     title=news.split('<CONTENT>')[0]
#     title=title.replace(create_d+'<TITLE>','')
#     contents=news.split('<CREATE_DATE>')[0]
#     contents=contents.replace(create_d+'<TITLE>'+title+'<CONTENT>','')
with open('news_data.json', 'w', encoding='utf-8') as make_file:
    for idx, news in enumerate(news_data):
        news = news.replace('\n', '').replace('\r', '')
        create_d = news.split('<TITLE>')[0]
        title = news.split('<CONTENT>')[0]
        title = title.replace(create_d + '<TITLE>', '')
        contents = news.split('CREATE_DATE')[0]
        contents = contents.replace(create_d + '<TITLE>' + title + '<CONTENT>', '')
        #make_file.write('\n')
        file_data={'index':{'_id':'news'+str(idx)}}
        file_data = json.dump(file_data, make_file, ensure_ascii=False)
        make_file.write('\n')
        file_data = {'CREATE_DATE':create_d,'TITLE':title,'CONTENT':contents}
        file_data=json.dump(file_data,make_file,ensure_ascii=False)
        make_file.write('\n')
    #make_file.write('\n')
