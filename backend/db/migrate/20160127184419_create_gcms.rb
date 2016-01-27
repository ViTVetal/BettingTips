class CreateGcms < ActiveRecord::Migration
  def change
    create_table :gcms do |t|
    t.string :token
      t.timestamps null: false
    end
  end
end
