set "list=road_sign_prohib_4c.json road_sign_prohib_4d.json road_sign_prohib_5.json road_sign_prohib_6a.json road_sign_prohib_6b.json road_sign_prohib_6c.json road_sign_prohib_6d.json road_sign_prohib_7a.json road_sign_prohib_7al.json road_sign_prohib_7aw.json road_sign_prohib_7b.json road_sign_prohib_7c.json road_sign_prohib_7e.json road_sign_prohib_7f.json road_sign_prohib_8a.json road_sign_prohib_8b.json road_sign_prohib_8c.json road_sign_prohib_9a.json road_sign_prohib_9b.json road_sign_prohib_9c.json road_sign_prohib_9d.json road_sign_prohib_11.json road_sign_prohib_11a.json road_sign_prohib_11b.json road_sign_prohib_12.json road_sign_prohib_13a.json road_sign_prohib_13b.json road_sign_prohib_13d.json road_sign_prohib_13e.json road_sign_prohib_14"

for %%a in (%list%) do (
copy road_sign_mandat_16.json %%a
)

pause
